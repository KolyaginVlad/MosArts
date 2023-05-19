package ru.cpc.mosarts.ui.registration

import ru.cpc.mosarts.utils.base.Event

sealed class RegistrationScreenEvent : Event() {
	class ShowToast(val text: String) : RegistrationScreenEvent()
	object GoToMoreInf: RegistrationScreenEvent()
}