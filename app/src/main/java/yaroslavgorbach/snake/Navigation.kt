package yaroslavgorbach.snake

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import yaroslavgorbach.snake.presentation.game.GameScreen
import yaroslavgorbach.snake.presentation.menu.MenuScreen
import yaroslavgorbach.snake.presentation.score.HighScoreScreen
import yaroslavgorbach.snake.presentation.settings.SettingScreen


sealed class Screen(val route: String) {
    object Menu : Screen("Menu")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Menu : LeafScreen("Menu")
    object HighScores : LeafScreen("HighScores")
    object Settings : LeafScreen("Settings")
    object Game : LeafScreen("Game")
}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route,
        modifier = modifier,
    ) {
        addMenuTopLevel(navController)
    }
}

private fun NavGraphBuilder.addMenuTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Menu.route,
        startDestination = LeafScreen.Menu.createRoute(Screen.Menu),
    ) {
        addMenu(navController, Screen.Menu)
        addHighScores(navController, Screen.Menu)
        addSettings(navController, Screen.Menu)
        addGame(navController, Screen.Menu)
    }
}

private fun NavGraphBuilder.addMenu(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Menu.createRoute(root)) {
        MenuScreen(navigateToGame = {
            navController.navigate(LeafScreen.Game.createRoute(root))
        }, navigateToHighScore = {
            navController.navigate(LeafScreen.HighScores.createRoute(root))
        }, navigateToSetting = {
            navController.navigate(LeafScreen.Settings.createRoute(root))
        })
    }
}

private fun NavGraphBuilder.addGame(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Game.createRoute(root)) {
        GameScreen(onBack = {
            navController.popBackStack()
        })
    }
}

private fun NavGraphBuilder.addHighScores(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.HighScores.createRoute(root)) {
        HighScoreScreen(onBack = {
            navController.popBackStack()
        })
    }
}

private fun NavGraphBuilder.addSettings(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Settings.createRoute(root)) {
        SettingScreen {
            navController.popBackStack()
        }
    }
}


