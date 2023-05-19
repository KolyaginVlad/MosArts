package ru.cpc.mosarts.ui.auth

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
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.cpc.mosarts.R
import ru.cpc.mosarts.ui.theme.MosArtsTheme
import ru.cpc.mosarts.ui.views.Spacer

@RootNavGraph(start = true)
@Destination
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is AuthScreenEvent.ShowToast -> Toast.makeText(
                    context, it.text, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    AuthScreenContent(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onAuth = viewModel::onAuth
    )
}

@Composable
fun AuthScreenContent(
    state: AuthScreenState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
            Text(text = stringResource(id = R.string.auth))
            Spacer(32.dp)
            TextField(value = state.login, onValueChange = onLoginChange)
            Spacer(16.dp)
            TextField(value = state.password, onValueChange = onPasswordChange)
            Spacer(32.dp)
            Button(enabled = state.isLoading.not(), onClick = onAuth) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    Text(text = stringResource(id = R.string.auth))
                }
            }
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    MosArtsTheme {
        AuthScreenContent(AuthScreenState("123", "321"), { }, { }, { })
    }
}