package ru.cpc.mosarts.ui.authorization.auth

import com.vk.api.sdk.auth.VKAuthenticationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.usecases.GetVkProfileInfoUseCase
import ru.cpc.mosarts.domain.usecases.SaveTokenUseCase
import ru.cpc.mosarts.domain.usecases.LoginUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getVkProfileInfoUseCase: GetVkProfileInfoUseCase,
) : BaseViewModel<AuthScreenState, AuthScreenEvent>(AuthScreenState()) {

    fun onLoginChange(login: String) {
        updateState {
            it.copy(email = login)
        }
    }

    fun onPasswordChange(password: String) {
        updateState {
            it.copy(password = password)
        }
    }

    fun onAuth() = launchViewModelScope {
        updateState {
            it.copy(isLoading = true)
        }
        loginUseCase(UserCredentials(currentState.email, currentState.password)).fold(
            onFailure = {
                sendEvent(AuthScreenEvent.ShowToast(it.message ?: "Something went wrong"))
            }, onSuccess = {
                //sendEvent(AuthScreenEvent.GoToList)
                // TODO:
            }
        )
        updateState {
            AuthScreenState() //Сбрасываю на начальное состояние
        }
    }

    fun onGetVkToken(vkAuthenticationResult: VKAuthenticationResult?) {
        if (vkAuthenticationResult is VKAuthenticationResult.Success) {
            launchViewModelScope {
                saveTokenUseCase(vkAuthenticationResult.token.accessToken)
                getVkProfileInfoUseCase().fold(
                    onSuccess = {
                        sendEvent(
                            AuthScreenEvent.GoToMoreInf(
                                it
                            )
                        )
                    },
                    onFailure = ::handleException
                )
            }
        }
    }

    fun onVkAuth() {
        trySendEvent(AuthScreenEvent.LoginVk)
    }
}
