package yaroslavgorbach.snake.presentation.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.presentation.common.ui.AppButton
import yaroslavgorbach.snake.presentation.common.ui.DisplayLarge

@Composable
fun MenuScreen(navigateToGame: () -> Unit, navigateToHighScore: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        DisplayLarge(text = stringResource(id = R.string.app_name))

        AppButton(
            modifier = Modifier
                .width(248.dp)
                .padding(top = 64.dp),
            text = stringResource(R.string.new_game)
        ) {
            navigateToGame()
        }

        AppButton(
            modifier = Modifier.width(248.dp),
            text = stringResource(id = R.string.high_score)
        ) {
            navigateToHighScore()
        }

        AppButton(modifier = Modifier.width(248.dp), text = stringResource(R.string.settings)) {
            // TODO: navigate to Settings
        }

        AppButton(modifier = Modifier.width(248.dp), text = stringResource(R.string.about)) {
            // TODO: navigate to About
        }
    }
}