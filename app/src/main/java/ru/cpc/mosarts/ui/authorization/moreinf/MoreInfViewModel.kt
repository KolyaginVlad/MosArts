package ru.cpc.mosarts.ui.authorization.moreinf

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

    fun onAgeChange(age: String) {
        if (age.isDigitsOnly()) {
            updateState {
                it.copy(age = age)
            }
        }
    }


    fun onPhoneNumberChange(number: String) {
        updateState {
            it.copy(phoneNumber = number)
        }
    }

    fun onNameChange(name: String) {
        updateState {
            it.copy(name = name, nameError = false)
        }
    }

    fun onFatherNameChange(name: String) {
        updateState {
            it.copy(fatherName = name)
        }
    }

    fun onSurnameChange(name: String) {
        updateState {
            it.copy(surname = name, surnameError = false)
        }
    }

    fun onAuth() = launchViewModelScope {
        if (currentState.name.isBlank() || currentState.surname.isBlank()){
            updateState {
                it.copy(
                    nameError = it.name.isBlank(),
                    surnameError = it.surname.isBlank()
                )
            }
            return@launchViewModelScope
        }
        updateState {
            it.copy(isLoading = true)
        }
        moreInfUseCase(
            MoreInf(
                age = currentState.age.toIntOrNull(),
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
                sendEvent(MoreInfScreenEvent.GoToTest)
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
