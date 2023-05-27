package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import kotlinx.collections.immutable.PersistentList
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.TestParams
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

abstract class GetSimpleTestUseCase : UseCase<PersistentList<Question>, TestParams>()

class GetSimpleTestUseCaseImpl @Inject constructor(
	private val testRepository: TestRepository,
) : GetSimpleTestUseCase() {
	override suspend fun run(args: TestParams): Result<PersistentList<Question>> =
		testRepository.getSimpleTest(args)
}