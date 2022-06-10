package yaroslavgorbach.snake.domain.game

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
        snake = listOf(Pair(7, 7)),
        currentDirection = SnakeDirection.RIGHT
    )

    private val mutex = Mutex()

    private val mutableState = MutableStateFlow(startGameState)

    val gameState: Flow<GameState> = mutableState

    private val currentDirection = mutableStateOf(SnakeDirection.RIGHT)

    private val gameJob: Job = startGame()

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    private fun reset() {
        mutableState.update { startGameState }
        currentDirection.value = SnakeDirection.RIGHT
        move = Pair(1, 0)
    }

    suspend fun stop() {
        gameJob.cancelAndJoin()
    }

    fun startGame(): Job {
        reset()
        return scope.launch {
            var snakeLength = 2

            while (isActive) {
                delay(150)

                Log.i("dssddfsd", mutableState.value.snake.first().toString())
                mutableState.update {
                    val hasReachedVerticalBorder = it.snake.first().second == BOARD_SIZE.dec()
                    val hasReachedHorizontalBorder = it.snake.first().first == BOARD_SIZE.dec()

                    if (hasReachedHorizontalBorder || hasReachedVerticalBorder) {
                        onGameEnded.invoke()
                        stop()
                    }

                    if (move.first == 0 && move.second == -1) {
                        currentDirection.value = SnakeDirection.UP
                    } else if (move.first == -1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.LEFT
                    } else if (move.first == 1 && move.second == 0) {
                        currentDirection.value = SnakeDirection.RIGHT
                    } else if (move.first == 0 && move.second == 1) {
                        currentDirection.value = SnakeDirection.DOWN
                    }

                    val newPosition = it.snake.first().let { poz ->
                        mutex.withLock {
                            Pair(
                                (poz.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                                (poz.second + move.second + BOARD_SIZE) % BOARD_SIZE
                            )
                        }
                    }

                    if (newPosition == it.food) {
                        onFoodEaten.invoke()
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        onGameEnded.invoke()
                        stop()
                    }

                    it.copy(
                        food = if (newPosition == it.food) Pair(
                            Random().nextInt(BOARD_SIZE),
                            Random().nextInt(BOARD_SIZE)
                        ) else it.food,
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        currentDirection = currentDirection.value,
                    )
                }
            }
        }
    }
}