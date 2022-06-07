package yaroslavgorbach.snake.presentation.game.model

sealed class GameActions {
    class Move(val pair: Pair<Int, Int>) : GameActions()
}