package ru.cpc.mosarts.ui.registration

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.destinations.MoreInfScreenDestination
import ru.cpc.mosarts.ui.views.FormTextField
import ru.cpc.mosarts.ui.views.Spacer


@RootNavGraph(start = true)
@Destination
@Composable
fun RegistrationScreen(
	navigator: DestinationsNavigator,
	viewModel: RegistrationViewModel = hiltViewModel()
) {
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		viewModel.event.collect {
			when (it) {
				is RegistrationScreenEvent.ShowToast -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()
				
				is RegistrationScreenEvent.GoToMoreInf -> navigator.navigate(
					MoreInfScreenDestination
				)
			}
		}
	}
	RegistrationScreenContent(
		state = state,
		onLoginChange = viewModel::onLoginChange,
		onPasswordChange = viewModel::onPasswordChange,
		onAuth = viewModel::onAuth,
		onSecondPasswordChange = viewModel::onSecondPasswordChange
	)
}

@Composable
fun RegistrationScreenContent(
	state: RegistrationScreenState,
	onLoginChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	onSecondPasswordChange: (String) -> Unit,
	onAuth: () -> Unit,
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
			Text(text = stringResource(id = R.string.registration))
			Spacer(32.dp)
			FormTextField(
				value = state.email,
				onValueChange = onLoginChange,
				label = {
					Text(text = stringResource(id = R.string.email))
				}
			)
			Spacer(16.dp)
			FormTextField(
				value = state.password,
				onValueChange = onPasswordChange,
				label = ({
					Text(text = stringResource(id = R.string.password))
				})
			)
			Spacer(16.dp)
			FormTextField(
				value = state.secondPassword,
				onValueChange = onSecondPasswordChange,
				label = {
					Text(text = stringResource(id = R.string.second_password))
				}
			)
			Spacer(32.dp)
			Button(enabled = state.isLoading.not(), onClick = onAuth) {
				if (state.isLoading) {
					CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
				} else {
					Text(text = stringResource(id = R.string.registration))
				}
			}
		}
	}
}