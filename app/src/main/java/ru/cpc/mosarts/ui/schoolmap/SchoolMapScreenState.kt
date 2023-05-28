package ru.cpc.mosarts.ui.schoolmap

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.domain.models.School
import ru.cpc.mosarts.ui.models.SchoolMapInfo
import ru.cpc.mosarts.utils.base.State

data class SchoolMapScreenState(
    val listOfSchools: ImmutableList<SchoolMapInfo> = persistentListOf(),
    val alertDialogInfo: School? = null,
    val filter: String = "",
) : State()
