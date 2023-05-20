package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class SimpleTestUseCase : UseCase<PersistentList<Question>, Unit>()

class SimpleTestUseCaseImpl @Inject constructor(
	private val testRepository: TestRepository,
) : SimpleTestUseCase() {
	override suspend fun run(args: Unit): Result<PersistentList<Question>> =
		testRepository.getSimpleTest()
}