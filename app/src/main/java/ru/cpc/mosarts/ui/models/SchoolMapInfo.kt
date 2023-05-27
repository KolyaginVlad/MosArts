package ru.cpc.mosarts.ui.models

import ru.cpc.mosarts.domain.models.School

data class SchoolMapInfo(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)

fun School.mapToUi() =
    SchoolMapInfo(
        id = id, name = name, latitude = latitude, longitude = longitude
    )