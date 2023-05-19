package ru.cpc.mosarts.domain.models

data class RegistrateUserCredentials(
	val email: String,
	val password: String,
	val secondpassword: String
)
