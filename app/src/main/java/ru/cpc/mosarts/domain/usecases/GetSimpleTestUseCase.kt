package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.NoArgsUseCase
import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class GetSimpleTestUseCase : NoArgsUseCase<PersistentList<Question>>()

class GetSimpleTestUseCaseImpl @Inject constructor(
	private val testRepository: TestRepository,
) : GetSimpleTestUseCase() {
	override suspend fun run(): Result<PersistentList<Question>> =
		testRepository.getSimpleTest()
}