package ru.cpc.mosarts.data.models

import ru.cpc.mosarts.domain.models.UserCredentials


data class UserCredentialsDtoe(
    val email: String,
    val password: String,
)

fun UserCredentials.mapToDtoe() =
    UserCredentialsDtoe(email, password)