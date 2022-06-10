package yaroslavgorbach.snake.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yaroslavgorbach.snake.data.GameCache
import yaroslavgorbach.snake.presentation.settings.model.SettingsActions
import yaroslavgorbach.snake.presentation.settings.model.SettingsViewState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val gameCache: GameCache) : ViewModel() {
    private val pendingActions = MutableSharedFlow<SettingsActions>()

    private val _state: MutableStateFlow<SettingsViewState> = MutableStateFlow(SettingsViewState())

    val state: StateFlow<SettingsViewState>
        get() = _state

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is SettingsActions.NewName -> _state.update { it.copy(name = action.name) }
                    SettingsActions.SaveName -> {
                        gameCache.savePlayerName(state.value.name)
                    }
                }
            }
        }
    }

    fun submitAction(action: SettingsActions) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }
}