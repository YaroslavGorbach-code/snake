package yaroslavgorbach.snake.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.presentation.common.ui.AppBar
import yaroslavgorbach.snake.presentation.common.ui.AppButton
import yaroslavgorbach.snake.presentation.common.ui.DisplayLarge
import yaroslavgorbach.snake.presentation.common.ui.TitleLarge

@Composable
fun EndScreen(score: Int, onTryAgain: () -> Unit) {

    AppBar(title = "", onBackClicked = { }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayLarge(
                modifier = Modifier.padding(8.dp),
                text = stringResource(R.string.game_over),
                textAlign = TextAlign.Center
            )

            TitleLarge(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.your_score, score),
            )

            AppButton(text = stringResource(R.string.try_again)) { onTryAgain.invoke() }
        }
    }
}