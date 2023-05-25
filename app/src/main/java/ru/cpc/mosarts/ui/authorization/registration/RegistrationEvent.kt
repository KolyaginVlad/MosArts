package ru.cpc.mosarts.ui.authorization.registration

import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.utils.base.Event

sealed class RegistrationScreenEvent : Event() {
	class ShowToast(val text: String) : RegistrationScreenEvent()
	class GoToMoreInf(val profileInfo: ProfileInfo? = null): RegistrationScreenEvent()
	object LoginVk : RegistrationScreenEvent()
}