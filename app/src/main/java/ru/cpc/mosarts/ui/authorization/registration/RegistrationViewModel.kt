package ru.cpc.mosarts.ui.authorization.registration

import com.vk.api.sdk.auth.VKAuthenticationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.usecases.GetVkProfileInfoUseCase
import ru.cpc.mosarts.domain.usecases.RegistrationUseCase
import ru.cpc.mosarts.domain.usecases.SaveTokenUseCase
import ru.cpc.mosarts.ui.authorization.auth.AuthScreenEvent
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getVkProfileInfoUseCase: GetVkProfileInfoUseCase,
) : BaseViewModel<RegistrationScreenState, RegistrationScreenEvent>(RegistrationScreenState()) {
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

    fun onSecondPasswordChange(password: String) {
        updateState {
            it.copy(secondPassword = password)
        }
    }

    fun onAuth() = launchViewModelScope {
        updateState {
            it.copy(isLoading = true)
        }
        if (currentState.checkPasswords()) {
            registrationUseCase(UserCredentials(currentState.email, currentState.password))
                .fold(
                    onFailure = {
                        sendEvent(
                            RegistrationScreenEvent.ShowToast(
                                it.message ?: "Something went wrong"
                            )
                        )
                    }, onSuccess = {
                        sendEvent(RegistrationScreenEvent.GoToMoreInf())
                    }
                )
            updateState {
                RegistrationScreenState() //Сбрасываю на начальное состояние
            }
        } else {
            sendEvent(RegistrationScreenEvent.ShowToast("Different passwords"))
            updateState {
                it.copy(isLoading = false)
            }
        }
    }

    fun onGetVkToken(vkAuthenticationResult: VKAuthenticationResult?) {
        if (vkAuthenticationResult is VKAuthenticationResult.Success) {
            launchViewModelScope {
                saveTokenUseCase(vkAuthenticationResult.token.accessToken)
                getVkProfileInfoUseCase().fold(
                    onSuccess = { profile ->
                        registrationUseCase(
                            UserCredentials(
                                vkAuthenticationResult.token.email ?: kotlin.run {
                                    sendEvent(RegistrationScreenEvent.CantLoginByVk)
                                    return@launchViewModelScope
                                },
                                vkAuthenticationResult.token.userId.value.toString()
                            )
                        ).fold(
                            onFailure = {
                                sendEvent(
                                    RegistrationScreenEvent.ShowToast(
                                        it.message ?: "Something went wrong"
                                    )
                                )
                            }, onSuccess = {
                                sendEvent(
                                    RegistrationScreenEvent.GoToMoreInf(
                                        profile
                                    )
                                )
                            }
                        )
                    },
                    onFailure = {
                        handleException(it)
                        sendEvent(
                            RegistrationScreenEvent.ShowToast(
                                it.message ?: "Something went wrong"
                            )
                        )
                    }
                )
            }
        }
    }

    fun onVkAuth() {
        trySendEvent(RegistrationScreenEvent.LoginVk)
    }

    fun onGoToAuth() {
        trySendEvent(RegistrationScreenEvent.GoToAuth)
    }

    private fun RegistrationScreenState.checkPasswords() = secondPassword == password

}