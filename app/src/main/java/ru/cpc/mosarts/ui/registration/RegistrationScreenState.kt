package ru.cpc.mosarts.ui.registration

import ru.cpc.mosarts.utils.base.State

data class RegistrationScreenState(
	val email: String = "",
	val password: String = "",
	val secondPassword: String = "",
	val isLoading: Boolean = false
) : State()
