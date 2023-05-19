package ru.cpc.mosarts.domain.usecases

import com.osinit.mycompany.domain.usecases.common.UseCase
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository
import javax.inject.Inject

abstract class LoginUseCase : UseCase<Unit, UserCredentials>()

class LoginUseCaseImpl @Inject constructor(
    private val apiRepository: ApiRepository,
) : LoginUseCase() {
    override suspend fun run(args: UserCredentials): Result<Unit> =
        apiRepository.login(args)
}