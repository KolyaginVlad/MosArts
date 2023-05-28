package ru.cpc.mosarts.ui.authorization.registration

import ru.cpc.mosarts.utils.base.State

data class RegistrationScreenState(
    val email: String = "",
    val password: String = "",
    val secondPassword: String = "",
    val isLoading: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val secondPasswordError: Boolean = false,
) : State()
