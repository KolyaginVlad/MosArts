package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository
import javax.inject.Inject

abstract class RegistrationUseCase : UseCase<Unit, UserCredentials>()

class RegistrationUseCaseImpl @Inject constructor(
	private val apiRepository: ApiRepository,
) : RegistrationUseCase() {
	override suspend fun run(args: UserCredentials): Result<Unit> =
		apiRepository.registration(args)
}