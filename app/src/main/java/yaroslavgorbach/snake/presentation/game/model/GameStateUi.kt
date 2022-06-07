package yaroslavgorbach.snake.presentation.game.model

import yaroslavgorbach.snake.data.model.GameState
import yaroslavgorbach.snake.data.model.HighScore

data class GameViewState(
    val gameState: GameState = GameState.Empty,
    val score: Int = 0,
    val isFinish: Boolean = false,
    val playerName: String = "",
    val highScores: List<HighScore> = emptyList()
)