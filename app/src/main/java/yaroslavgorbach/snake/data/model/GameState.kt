package yaroslavgorbach.snake.data.model

import yaroslavgorbach.snake.domain.game.SnakeDirection

data class GameState(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    val currentDirection: SnakeDirection
) {
    companion object {
        val Empty = GameState(Pair(0, 0), emptyList(), SnakeDirection.UP)
    }
}