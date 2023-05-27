package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.UserRepository
import javax.inject.Inject

abstract class LoginUseCase : UseCase<Unit, UserCredentials>()

class LoginUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : LoginUseCase() {
    override suspend fun run(args: UserCredentials): Result<Unit> =
        userRepository.login(args)
}