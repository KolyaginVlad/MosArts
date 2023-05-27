package ru.cpc.mosarts.ui.authorization.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vk.api.sdk.auth.VKScope
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.auth.Synchronizer
import ru.cpc.mosarts.ui.destinations.ActivitiesScreenDestination
import ru.cpc.mosarts.ui.destinations.MoreInfScreenDestination
import ru.cpc.mosarts.ui.destinations.RegistrationScreenDestination
import ru.cpc.mosarts.ui.theme.MosArtsTheme
import ru.cpc.mosarts.ui.views.FormTextField
import ru.cpc.mosarts.ui.views.Spacer
import ru.cpc.mosarts.utils.navigateWithClearBackStack
import androidx.compose.foundation.layout.Spacer as ModifiableSpacer

@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is AuthScreenEvent.ShowToast -> Toast.makeText(
                    context, it.text, Toast.LENGTH_LONG
                ).show()

                AuthScreenEvent.LoginVk -> {
                    Synchronizer.login(listOf(VKScope.EMAIL, VKScope.FRIENDS))
                }

                is AuthScreenEvent.GoToMoreInf -> navigator.navigateWithClearBackStack(
                    MoreInfScreenDestination(it.profileInfo)
                )

                AuthScreenEvent.GoToRegister -> navigator.navigateWithClearBackStack(
                    RegistrationScreenDestination
                )

                AuthScreenEvent.CantLoginByVk -> Toast.makeText(
                    context, context.getString(R.string.cant_login_by_vk), Toast.LENGTH_LONG
                ).show()

                AuthScreenEvent.GoToActivities -> navigator.navigateWithClearBackStack(
                    ActivitiesScreenDestination
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        Synchronizer.token.collect {
            viewModel.onGetVkToken(it)
        }
    }
    AuthScreenContent(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onAuth = viewModel::onAuth,
        onVkAuth = viewModel::onVkAuth,
        onGoToRegister = viewModel::onGoToRegister
    )
}

@Composable
fun AuthScreenContent(
    state: AuthScreenState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAuth: () -> Unit,
    onVkAuth: () -> Unit,
    onGoToRegister: () -> Unit,
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
            ModifiableSpacer(modifier = Modifier.weight(1f))
            Text(text = stringResource(id = R.string.auth))
            Spacer(32.dp)
            FormTextField(
                value = state.email,
                onValueChange = onLoginChange,
                label = {
                    Text(
                        text = stringResource(id = R.string.email)
                    )
                }
            )
            Spacer(16.dp)
            FormTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = {
                    Text(
                        text = stringResource(id = R.string.password)
                    )
                }
            )
            Spacer(32.dp)
            Button(enabled = state.isLoading.not(), onClick = onAuth) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    Text(text = stringResource(id = R.string.auth))
                }
            }
            Spacer(32.dp)
            Image(
                modifier = Modifier
					.size(48.dp)
					.clickable(onClick = onVkAuth),
                painter = painterResource(R.drawable.vk),
                contentDescription = stringResource(R.string.auth_by_vk)
            )
            ModifiableSpacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onGoToRegister) {
                Text(text = stringResource(id = R.string.register))
            }
        }
    }
}


@Preview
@Composable
private fun AuthScreenPreview() {
    MosArtsTheme {
        AuthScreenContent(AuthScreenState("123", "321"), { }, { }, { }, { }, { })
    }
}