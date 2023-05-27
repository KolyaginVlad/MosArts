package ru.cpc.mosarts.domain.models

data class School(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val address: String,
    val email: String,
    val description: String,
    val themes: List<ThemeData>
)
