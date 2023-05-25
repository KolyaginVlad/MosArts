package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class SendSimpleTestUseCase : UseCase<Unit, TestResults>()

class SendSimpleTestUseCaseImpl @Inject constructor(
	private val testRepository: TestRepository,
) : SendSimpleTestUseCase() {
	override suspend fun run(args: TestResults): Result<Unit> =
		testRepository.sendSimpleTest(args)
}