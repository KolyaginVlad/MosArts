package ru.cpc.mosarts.domain.repositories

import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.TestResults

interface TestRepository {
	suspend fun getSimpleTest(): Result<PersistentList<Question>>
	suspend fun sendSimpleTest(answers: TestResults): Result<Unit>
}