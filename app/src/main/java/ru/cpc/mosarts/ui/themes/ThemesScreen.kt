package ru.cpc.mosarts.ui.themes

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.destinations.ActivitiesScreenDestination
import ru.cpc.mosarts.ui.destinations.SchoolMapScreenDestination
import ru.cpc.mosarts.ui.themes.models.ThemeUiData
import ru.cpc.mosarts.ui.themes.views.ThemesCheckBoxGroup
import ru.cpc.mosarts.ui.views.Loading
import ru.cpc.mosarts.utils.navigateWithClearBackStack

@Composable
@Destination
fun ThemesScreen(
    navigator: DestinationsNavigator,
    viewModel: ThemesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                ThemesScreenEvent.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

                ThemesScreenEvent.GoToMap -> navigator.navigateWithClearBackStack(
                    ActivitiesScreenDestination
                )
            }
        }
    }
    Content(
        state = state,
        onSelectTheme = viewModel::onSelectTheme,
        onDone = viewModel::onDone
    )
}

@Composable
private fun Content(
    state: ThemesScreenState,
    onSelectTheme: (ThemeUiData, Boolean) -> Unit,
    onDone: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                Loading()
            } else {
                Text(text = stringResource(id = R.string.select_themes))
                ThemesCheckBoxGroup(listOfThemes = state.themes, onThemeSelected = onSelectTheme)
                Button(onClick = onDone) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }
    }
}