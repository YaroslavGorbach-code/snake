package yaroslavgorbach.snake

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import yaroslavgorbach.snake.domain.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Composable
    override fun Content() {
        AppNavigation(navController = rememberNavController())
    }

}