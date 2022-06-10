package yaroslavgorbach.snake.presentation.settings.model

sealed class SettingsActions {
    class NewName(val name: String) : SettingsActions()
    object SaveName : SettingsActions()
}