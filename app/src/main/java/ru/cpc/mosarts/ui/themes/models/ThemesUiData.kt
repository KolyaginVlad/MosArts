package ru.cpc.mosarts.ui.themes.models

import ru.cpc.mosarts.domain.models.ThemeData

data class ThemeUiData(
    val id: Int,
    val name: String,
    val isSelected: Boolean,
)

fun ThemeData.mapToUi(isSelected: Boolean) =
    ThemeUiData(
        id, name, isSelected
    )

fun ThemeUiData.mapToDomain() =
    ThemeData(
        id, name
    )
