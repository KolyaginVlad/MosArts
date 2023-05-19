package ru.cpc.mosarts.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
) : BaseViewModel<AuthScreenState, AuthScreenEvent>(AuthScreenState()) {

    fun onLoginChange(login: String) {
        updateState {
            it.copy(login = login)
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
        apiRepository.login(UserCredentials(currentState.login, currentState.password)).fold(
            onFailure = {
                sendEvent(AuthScreenEvent.ShowToast(it.message ?: "Something went wrong"))
            }, onSuccess = {
//                sendEvent(AuthScreenEvent.GoToList)
            }
        )
        updateState {
            AuthScreenState() //Сбрасываю на начальное состояние
        }
    }
}
