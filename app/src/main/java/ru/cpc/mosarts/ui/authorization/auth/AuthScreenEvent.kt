package ru.cpc.mosarts.ui.authorization.auth

import ru.cpc.mosarts.domain.models.ProfileInfo
import ru.cpc.mosarts.utils.base.Event

sealed class AuthScreenEvent : Event() {
	class ShowToast(val text: String) : AuthScreenEvent()

    object LoginVk: AuthScreenEvent()

    class GoToMoreInf(val profileInfo: ProfileInfo? = null): AuthScreenEvent()

}