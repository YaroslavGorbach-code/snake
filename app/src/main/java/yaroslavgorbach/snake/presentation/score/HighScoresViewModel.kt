package yaroslavgorbach.snake.presentation.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import yaroslavgorbach.snake.data.GameCache
import yaroslavgorbach.snake.presentation.game.model.GameActions
import yaroslavgorbach.snake.presentation.game.model.GameViewState
import yaroslavgorbach.snake.presentation.score.model.HighScoresActions
import yaroslavgorbach.snake.presentation.score.model.HighScoresStateUi
import javax.inject.Inject

@HiltViewModel
class HighScoresViewModel @Inject constructor(private val gameCache: GameCache) : ViewModel() {

    private val pendingActions = MutableSharedFlow<HighScoresActions>()

    private val _state: MutableStateFlow<HighScoresStateUi> = MutableStateFlow(HighScoresStateUi())

    val state: StateFlow<HighScoresStateUi>
        get() = _state

    init {
        viewModelScope.launch {
            gameCache.getHighScores.map { score ->
                score.sortedByDescending { it.score }
            }.map { score ->
                score.take(10)
            }.collect { scores ->
                _state.update { it.copy(highScores = scores) }
            }
        }
    }

    fun submitAction(action: HighScoresActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}