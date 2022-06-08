package yaroslavgorbach.snake.presentation.score

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.data.model.HighScore
import yaroslavgorbach.snake.presentation.common.ui.AppBar
import yaroslavgorbach.snake.presentation.common.ui.TitleLarge

@Composable
fun HighScoreScreen() {
    val viewMode: HighScoresViewModel = hiltViewModel()

    HighScoreScreen(viewMode)
}

@Composable
fun HighScoreScreen(viewModel: HighScoresViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()

    AppBar(
        title = stringResource(R.string.high_score),
        onBackClicked = { }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TitleLarge(
                    text = stringResource(R.string.player_name),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                TitleLarge(
                    text = stringResource(R.string.score),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.highScores) {
                    HighScoreItem(it)
                }
            }
        }
    }

}

@Composable
private fun HighScoreItem(highScore: HighScore) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TitleLarge(
            text = highScore.playerName,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        TitleLarge(
            text = highScore.score.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}