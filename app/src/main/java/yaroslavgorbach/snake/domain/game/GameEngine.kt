package yaroslavgorbach.snake.domain.game

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import yaroslavgorbach.snake.data.model.GameState
import java.util.*

class GameEngine(
    private val scope: CoroutineScope,
    private val onGameEnded: () -> Unit,
    private val onFoodEaten: () -> Unit
) {

    companion object {
        const val BOARD_SIZE = 32
    }

    private val startGameState = GameState(
        food = Pair(5, 5),
        snake = listOf(Pair(7, 7), Pair(7, 7)),
        currentDirection = SnakeDirection.RIGHT
    )

    private val mutex = Mutex()

    private val mutableState = MutableStateFlow(startGameState)

    val gameState: Flow<GameState> = mutableState

    private var gameJob: Job? = null

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    init {
        startGame()
    }

    suspend fun reset() {
        mutableState.emit(startGameState)
        move = Pair(1, 0)
    }

    suspend fun stop() {
        gameJob?.cancelAndJoin()
    }

    fun startGame() {
        gameJob = scope.launch {
            reset()

            var snakeLength = 2

            while (isActive) {
                delay(150)

                mutableState.update {
                    checkReachedBorder(it)
                    calculateCurrentDirection(move)

                    val newPosition = getNewPosition(it)
                    if (newPosition == it.food) {
                        onFoodEaten.invoke()
                        snakeLength++
                    }
                    if (it.snake.contains(newPosition)) {
                        onGameEnded.invoke()
                        stop()
                    }

                    it.copy(
                        food = calculateFoodPosition(newPosition, it),
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        currentDirection = calculateCurrentDirection(move),
                    )
                }
            }
        }
    }

    private suspend fun checkReachedBorder(it: GameState) {
        val hasReachedLeftEnd = it.snake[1].first == 0
                && it.currentDirection == SnakeDirection.LEFT

        val hasReachedTopEnd = it.snake[1].second == 0
                && it.currentDirection == SnakeDirection.UP

        val hasReachedRightEnd = it.snake[1].first == BOARD_SIZE.dec()
                && it.currentDirection == SnakeDirection.RIGHT

        val hasReachedBottomEnd = it.snake[1].second == BOARD_SIZE.dec()
                && it.currentDirection == SnakeDirection.DOWN
        

        if (hasReachedLeftEnd || hasReachedTopEnd || hasReachedRightEnd || hasReachedBottomEnd) {
            onGameEnded.invoke()
            stop()
        }
    }

    private suspend fun getNewPosition(it: GameState): Pair<Int, Int> {
        return it.snake.first().let { poz ->
            Pair(
                (poz.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                (poz.second + move.second + BOARD_SIZE) % BOARD_SIZE
            )

        }
    }

    private fun calculateFoodPosition(
        newPosition: Pair<Int, Int>,
        it: GameState
    ) = if (newPosition == it.food) Pair(
        Random().nextInt(BOARD_SIZE),
        Random().nextInt(BOARD_SIZE)
    ) else it.food

    private fun calculateCurrentDirection(move: Pair<Int, Int>): SnakeDirection {
        if (move.first == 0 && move.second == -1) {
            return SnakeDirection.UP
        }
        if (move.first == -1 && move.second == 0) {
            return SnakeDirection.LEFT
        }
        if (move.first == 1 && move.second == 0) {
            return SnakeDirection.RIGHT
        }
        return SnakeDirection.DOWN
    }
}