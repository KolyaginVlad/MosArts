package ru.cpc.mosarts.ui.auth

import ru.cpc.mosarts.utils.base.Event

sealed class AuthScreenEvent : Event() {
	class ShowToast(val text: String) : AuthScreenEvent()
	object GoToList : AuthScreenEvent()
}