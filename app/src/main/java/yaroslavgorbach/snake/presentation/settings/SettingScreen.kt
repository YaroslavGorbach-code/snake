package yaroslavgorbach.snake.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.presentation.common.ui.AppBar
import yaroslavgorbach.snake.presentation.common.ui.AppButton
import yaroslavgorbach.snake.presentation.common.ui.DisplayLarge
import yaroslavgorbach.snake.presentation.settings.model.SettingsActions

@Composable
fun SettingScreen(onBack: () -> Unit) {
    val viewModel: SettingsViewModel = hiltViewModel()

    SettingScreen(viewModel = viewModel, actioner = viewModel::submitAction, onBack = onBack)
}

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel,
    actioner: (SettingsActions) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    AppBar(
        title = stringResource(R.string.title_settings),
        onBackClicked = { onBack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DisplayLarge(
                modifier = Modifier.padding(
                    top = 64.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                text = stringResource(id = R.string.player_name),
                textAlign = TextAlign.Center
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            TextField(
                value = state.name,
                onValueChange = { actioner(SettingsActions.NewName(it)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                ),
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground)
            )
            AppButton(
                text = stringResource(R.string.save), modifier = Modifier
                    .width(248.dp)
                    .padding(16.dp)
            ) {
                actioner(SettingsActions.SaveName)
                Toast.makeText(context, R.string.player_name_updated, Toast.LENGTH_SHORT).show()
//                    navController.popBackStack()

            }
        }
    }
}