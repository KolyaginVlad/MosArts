package ru.cpc.mosarts.ui.authorization.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.usecases.LoginUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase,
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
}
