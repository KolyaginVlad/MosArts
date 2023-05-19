package ru.cpc.mosarts.data.repositories

import javax.inject.Inject
import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository

class ApiRepositoryImpl @Inject constructor() : ApiRepository {
    override suspend fun login(userCredentials: UserCredentials): Result<Unit> {
        delay(1000)
        return if (Math.random() > 0.5) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalAccessException("No"))
        }
    }
}
