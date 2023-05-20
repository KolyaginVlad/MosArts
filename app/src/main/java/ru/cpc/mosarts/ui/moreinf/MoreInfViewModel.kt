package ru.cpc.mosarts.ui.moreinf

import androidx.core.text.isDigitsOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cpc.mosarts.domain.models.MoreInf
import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.domain.usecases.MoreInfUseCase
import ru.cpc.mosarts.utils.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MoreInfViewModel @Inject constructor(
	private val moreInfUseCase: MoreInfUseCase,
) : BaseViewModel<MoreInfScreenState, MoreInfScreenEvent>(MoreInfScreenState()) {
	
	fun OnAgeChange(age: String) {
		if (age.isDigitsOnly()) {
			updateState {
				it.copy(age = age)
			}
		} else updateState {
			it.copy()
		}
	}
	
	
	fun OnPhoneNumberChange(number: String) {
		updateState {
			it.copy(phoneNumber = number)
		}
	}
	
	fun OnNameChange(name: String) {
		updateState {
			it.copy(name = name)
		}
	}
	
	fun OnFatherNameChange(name: String) {
		updateState {
			it.copy(fatherName = name)
		}
	}
	
	fun OnSurnameChange(name: String) {
		updateState {
			it.copy(surname = name)
		}
	}
	
	fun onAuth() = launchViewModelScope {
		updateState {
			it.copy(isLoading = true)
		}
		moreInfUseCase(
			MoreInf(
				age = currentState.age.toInt(),
				phonenumber = currentState.phoneNumber,
				avatar = currentState.avatar,
				name = currentState.name,
				fatherName = currentState.fatherName,
				surname = currentState.surname
			)
		).fold(
			onFailure = {
				sendEvent(MoreInfScreenEvent.ShowToast(it.message ?: "Something went wrong"))
			}, onSuccess = {
				// TODO:
			}
		)
		updateState {
			MoreInfScreenState() //Сбрасываю на начальное состояние
		}
	}

	fun setProfileInfo(profileInfo: ProfileInfo?) {
		if (profileInfo != null) {
			updateState {
				it.copy(name = profileInfo.name, surname = profileInfo.surname)
			}
			//Это ВК регистрация
		} else {
			//Это регистрация через почту
		}
	}
}
