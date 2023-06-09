package ru.cpc.mosarts.ui.authorization.auth

import ru.cpc.mosarts.utils.base.State

data class AuthScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
) : State()