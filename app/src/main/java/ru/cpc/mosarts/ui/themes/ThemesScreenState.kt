package ru.cpc.mosarts.ui.themes

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.ui.themes.models.ThemeUiData
import ru.cpc.mosarts.utils.base.State

data class ThemesScreenState(
    val isLoading: Boolean = true,
    val themes: PersistentList<ThemeUiData> = persistentListOf(),
) : State()