package ru.cpc.mosarts.ui.authorization.registration

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vk.api.sdk.auth.VKScope
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.auth.Synchronizer
import ru.cpc.mosarts.ui.destinations.AuthScreenDestination
import ru.cpc.mosarts.ui.destinations.MoreInfScreenDestination
import ru.cpc.mosarts.ui.theme.Black
import ru.cpc.mosarts.ui.views.FormTextField
import ru.cpc.mosarts.ui.views.Spacer
import ru.cpc.mosarts.utils.navigateWithClearBackStack
import androidx.compose.foundation.layout.Spacer as ModifiableSpacer


@RootNavGraph(start = true)
@Destination
@Composable
fun RegistrationScreen(
    navigator: DestinationsNavigator,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is RegistrationScreenEvent.ShowToast -> Toast.makeText(
                    context, it.text, Toast.LENGTH_LONG
                ).show()

                is RegistrationScreenEvent.GoToMoreInf -> navigator.navigateWithClearBackStack(
                    MoreInfScreenDestination(it.profileInfo)
                )

                RegistrationScreenEvent.LoginVk -> {
                    Synchronizer.login(listOf(VKScope.EMAIL, VKScope.FRIENDS))
                }

                RegistrationScreenEvent.GoToAuth -> navigator.navigateWithClearBackStack(
                    AuthScreenDestination
                )

                RegistrationScreenEvent.CantLoginByVk -> Toast.makeText(
                    context, context.getString(R.string.cant_login_by_vk), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    LaunchedEffect(Unit) {
        Synchronizer.token.collect {
            viewModel.onGetVkToken(it)
        }
    }
    RegistrationScreenContent(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onAuth = viewModel::onAuth,
        onSecondPasswordChange = viewModel::onSecondPasswordChange,
        onVkAuth = viewModel::onVkAuth,
        onGoToAuth = viewModel::onGoToAuth
    )
}

@Composable
fun RegistrationScreenContent(
    state: RegistrationScreenState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecondPasswordChange: (String) -> Unit,
    onAuth: () -> Unit,
    onVkAuth: () -> Unit,
    onGoToAuth: () -> Unit,
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
            Text(text = stringResource(id = R.string.registration), color = Black)
            Spacer(32.dp)
            FormTextField(
                value = state.email,
                onValueChange = onLoginChange,
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                isError = state.emailError
            )
            Spacer(16.dp)
            FormTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = ({
                    Text(text = stringResource(id = R.string.password))
                }),
                isError = state.passwordError,
                visualTransformation = remember {
                    {
                        TransformedText(
                            AnnotatedString("*".repeat(it.length)),
                            OffsetMapping.Identity
                        )
                    }
                }
            )
            Spacer(16.dp)
            FormTextField(
                value = state.secondPassword,
                onValueChange = onSecondPasswordChange,
                label = {
                    Text(text = stringResource(id = R.string.second_password))
                },
                isError = state.secondPasswordError,
                visualTransformation = remember {
                    {
                        TransformedText(
                            AnnotatedString("*".repeat(it.length)),
                            OffsetMapping.Identity
                        )
                    }
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
            Spacer(32.dp)
            Image(
                modifier = Modifier
					.size(48.dp)
					.clickable(onClick = onVkAuth),
                painter = painterResource(R.drawable.vk),
                contentDescription = stringResource(R.string.auth_by_vk)
            )
            ModifiableSpacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onGoToAuth) {
                Text(text = stringResource(id = R.string.i_have_account))
            }
        }
    }
}