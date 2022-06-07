package yaroslavgorbach.snake.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yaroslavgorbach.snake.data.GameCache
import yaroslavgorbach.snake.domain.game.GameEngine
import yaroslavgorbach.snake.presentation.game.model.GameActions
import yaroslavgorbach.snake.presentation.game.model.GameViewState
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(val dataStore: GameCache) : ViewModel() {

    private val pendingActions = MutableSharedFlow<GameActions>()

    private val _state: MutableStateFlow<GameViewState> = MutableStateFlow(GameViewState())

    val state: StateFlow<GameViewState>
        get() = _state

    private val gameEngine: GameEngine = GameEngine(viewModelScope, onGameEnded = {
        _state.update { it.copy(isFinish = true) }
    }, onFoodEaten = {
        _state.update { it.copy(score = it.score.inc()) }
    })

    init {
        viewModelScope.launch {
            dataStore.getPlayerName.collect() { name ->
                _state.update { it.copy(playerName = name) }
            }
        }

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is GameActions.Move -> {
                        gameEngine.move = action.pair
                    }
                }
            }
        }

        viewModelScope.launch {
            gameEngine.gameState.collect { gameState ->
                _state.update { it.copy(gameState = gameState) }
            }
        }
    }

    fun submitAction(action: GameActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

}