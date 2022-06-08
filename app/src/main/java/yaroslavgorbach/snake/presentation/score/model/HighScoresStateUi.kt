package yaroslavgorbach.snake.presentation.score.model

import yaroslavgorbach.snake.data.model.HighScore

data class HighScoresStateUi(
    val highScores: List<HighScore> = emptyList()
)