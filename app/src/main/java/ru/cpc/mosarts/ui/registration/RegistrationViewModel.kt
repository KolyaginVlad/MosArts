package ru.cpc.mosarts.ui.registration

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.usecases.RegistrationUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel

class RegistrationViewModel @Inject constructor(
	private val registrationUseCase: RegistrationUseCase,
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
			it.copy(secondpassword = password)
		}
	}
	
	fun onAuth() = launchViewModelScope {
		updateState {
			it.copy(isLoading = true)
		}
		if (checkPasswords()) {
			registrationUseCase.run(UserCredentials(currentState.email, currentState.password))
				.fold(
					onFailure = {
						sendEvent(
							RegistrationScreenEvent.ShowToast(
								it.message ?: "Something went wrong"
							)
						)
					}, onSuccess = {
						// TODO:
						sendEvent(RegistrationScreenEvent.GoToMoreInf)
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
	
	private fun checkPasswords() = currentState.secondpassword == currentState.password
	
}