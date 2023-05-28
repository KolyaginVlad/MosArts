package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.UserRepository
import javax.inject.Inject

abstract class RegistrationUseCase : UseCase<Unit, UserCredentials>()

class RegistrationUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : RegistrationUseCase() {
    override suspend fun run(args: UserCredentials): Result<Unit> =
        userRepository.registration(args)
}