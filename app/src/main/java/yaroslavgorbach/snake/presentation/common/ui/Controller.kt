package yaroslavgorbach.snake.presentation.common.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yaroslavgorbach.snake.domain.game.SnakeDirection

@Composable
fun Controller(onDirectionChange: (SnakeDirection) -> Unit) {
    val buttonSize = Modifier.size(64.dp)

    val currentDirection = remember { mutableStateOf(SnakeDirection.RIGHT) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        AppIconButton(icon = Icons.Default.KeyboardArrowUp) {
            if (currentDirection.value != SnakeDirection.DOWN) {
                onDirectionChange.invoke(SnakeDirection.UP)
                currentDirection.value = SnakeDirection.UP
            }
        }
        Row {
            AppIconButton(icon = Icons.Default.KeyboardArrowLeft) {
                if (currentDirection.value != SnakeDirection.RIGHT) {
                    onDirectionChange.invoke(SnakeDirection.LEFT)
                    currentDirection.value = SnakeDirection.LEFT
                }
            }

            Spacer(modifier = buttonSize)

            AppIconButton(icon = Icons.Default.KeyboardArrowRight) {
                if (currentDirection.value != SnakeDirection.LEFT) {
                    onDirectionChange.invoke(SnakeDirection.RIGHT)
                    currentDirection.value = SnakeDirection.RIGHT
                }
            }
        }

        AppIconButton(icon = Icons.Default.KeyboardArrowDown) {
            if (currentDirection.value != SnakeDirection.UP) {
                onDirectionChange.invoke(SnakeDirection.DOWN)
                currentDirection.value = SnakeDirection.DOWN
            }
        }
    }
}