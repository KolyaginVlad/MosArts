package ru.cpc.mosarts.ui.registration

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class RegistrationViewModel  @Inject constructor(
	private val apiRepository: ApiRepository,
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
	
	fun onAuth() = launchViewModelScope {
		updateState {
			it.copy(isLoading = true)
		}
		if (checkPasswords()){
		apiRepository.registration(UserCredentials(currentState.email, currentState.password)).fold(
			onFailure = {
				//logger.event(AuthAnalyticsEvent(false))
				sendEvent(RegistrationScreenEvent.ShowToast(it.message ?: "Something went wrong"))
			}, onSuccess = {
				//logger.event(AuthAnalyticsEvent(true))
				sendEvent(RegistrationScreenEvent.GoToMoreInf)
			}
		)
		updateState {
			RegistrationScreenState() //Сбрасываю на начальное состояние
		}
	}
		else {
			sendEvent(RegistrationScreenEvent.ShowToast( "Different passwords"))
		}
	}
	private fun checkPasswords() =	currentState.secondpassword==currentState.password
	
}