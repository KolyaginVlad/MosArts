package ru.cpc.mosarts.domain.repositories

import ru.cpc.mosarts.domain.models.ThemeData

interface TestRepository {

    suspend fun getThemes(): Result<List<ThemeData>>

    suspend fun sendSelectedThemes(themes: List<ThemeData>): Result<Unit>
}