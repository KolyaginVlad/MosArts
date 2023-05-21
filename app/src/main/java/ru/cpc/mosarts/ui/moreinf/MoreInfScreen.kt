package ru.cpc.mosarts.ui.moreinf

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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.R
import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.ui.destinations.ThemesScreenDestination
import ru.cpc.mosarts.ui.views.FormTextField
import ru.cpc.mosarts.ui.views.Spacer

@Destination
@Composable
fun MoreInfScreen(
	navigator: DestinationsNavigator,
	viewModel: MoreInfViewModel = hiltViewModel(),
	profileInfo: ProfileInfo?
) {
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		viewModel.setProfileInfo(profileInfo)
		viewModel.event.collect {
			when (it) {
				is MoreInfScreenEvent.ShowToast -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()

				MoreInfScreenEvent.GoToTest -> navigator.navigate(ThemesScreenDestination)
			}
		}
	}
	MoreInfScreenContent(
		state = state,
		onNameChange = viewModel::onNameChange,
		onSurnameChange = viewModel::onSurnameChange,
		onAuth = viewModel::onAuth,
		onFatherNameChange = viewModel::onFatherNameChange,
		onPhoneNumberChange = viewModel::onPhoneNumberChange,
		onAgeChange = viewModel::onAgeChange
	)
}

@Composable
fun MoreInfScreenContent(
	state: MoreInfScreenState,
	onNameChange: (String) -> Unit,
	onSurnameChange: (String) -> Unit,
	onFatherNameChange: (String) -> Unit,
	onPhoneNumberChange: (String) -> Unit,
	onAgeChange: (String) -> Unit,
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
				value = state.name,
				onValueChange = onNameChange,
				label = {
					Text(text = stringResource(id = R.string.name))
				}
			)
			Spacer(16.dp)
			FormTextField(
				value = state.surname,
				onValueChange = onSurnameChange,
				label = {
					Text(text = stringResource(id = R.string.surname))
				}
			)
			Spacer(16.dp)
			FormTextField(
				value = state.fatherName,
				onValueChange = onFatherNameChange,
				label = {
					Text(text = stringResource(id = R.string.father_name))
				}
			)
			Spacer(16.dp)
			FormTextField(
				value = state.phoneNumber,
				onValueChange = onPhoneNumberChange,
				label = {
					Text(text = stringResource(id = R.string.phone_number))
				}
			)
			Spacer(16.dp)
			FormTextField(
				value = state.age,
				onValueChange = onAgeChange,
				label = {
					Text(text = stringResource(id = R.string.age))
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