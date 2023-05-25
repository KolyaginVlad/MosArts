package ru.cpc.mosarts.domain.repositories


import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.models.ThemeData

interface TestRepository {
	suspend fun getSimpleTest(): Result<PersistentList<Question>>
	
	suspend fun sendSimpleTest(answers: TestResults): Result<Unit>
	
    suspend fun getThemes(): Result<List<ThemeData>>

    suspend fun sendSelectedThemes(themes: List<ThemeData>): Result<Unit>
}