package ru.cpc.mosarts.domain.repositories

import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question

interface TestRepository {
	suspend fun getSimpleTest(): Result<PersistentList<Question>>
}