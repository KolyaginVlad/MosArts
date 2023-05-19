package ru.cpc.mosarts.ui.auth

import ru.cpc.mosarts.utils.base.State

data class AuthScreenState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) : State()