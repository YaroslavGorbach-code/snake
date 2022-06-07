package yaroslavgorbach.snake.presentation.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yaroslavgorbach.snake.data.model.GameState
import yaroslavgorbach.snake.domain.game.GameEngine
import yaroslavgorbach.snake.presentation.common.ui.theme.DarkGreen

@Composable
fun Board(gameState: GameState) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / GameEngine.BOARD_SIZE

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, DarkGreen)
        )

        Box(
            Modifier
                .offset(x = tileSize * gameState.food.first, y = tileSize * gameState.food.second)
                .size(tileSize)
                .background(
                    DarkGreen, CircleShape
                )
        )

        gameState.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(
                        DarkGreen, RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}