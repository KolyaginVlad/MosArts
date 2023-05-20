package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.MoreInf
import ru.cpc.mosarts.domain.repositories.ApiRepository
import javax.inject.Inject

abstract class MoreInfUseCase : UseCase<Unit, MoreInf>()

class MoreInfUseCaseImpl @Inject constructor(
	private val apiRepository: ApiRepository,
) : MoreInfUseCase() {
	override suspend fun run(args: MoreInf): Result<Unit> =
		apiRepository.moreInf(args)
	
}