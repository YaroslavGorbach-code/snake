package yaroslavgorbach.snake.presentation.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.domain.game.SnakeDirection
import yaroslavgorbach.snake.presentation.common.ui.AppBar
import yaroslavgorbach.snake.presentation.common.ui.Board
import yaroslavgorbach.snake.presentation.common.ui.Controller
import yaroslavgorbach.snake.presentation.game.model.GameActions

@Composable
fun GameScreen() {
    val viewModel: GameViewModel = hiltViewModel()

    GameScreen(viewModel, actioner = viewModel::submitAction)
}

@Composable
fun GameScreen(viewModel: GameViewModel, actioner: (GameActions) -> Unit) {
    val state = viewModel.state.collectAsState().value

    AppBar(
        title = stringResource(id = R.string.your_score, state.score),
        onBackClicked = {

        }) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Board(state.gameState)

            Controller { direction ->
                when (direction) {
                    SnakeDirection.UP -> actioner(GameActions.Move(Pair(0, -1)))
                    SnakeDirection.LEFT -> actioner(GameActions.Move(Pair(-1, 0)))
                    SnakeDirection.RIGHT -> actioner(GameActions.Move(Pair(1, 0)))
                    SnakeDirection.DOWN -> actioner(GameActions.Move(Pair(0, 1)))
                }
            }
        }
    }
}