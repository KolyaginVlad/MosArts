package ru.cpc.mosarts.data.repositories

import ru.cpc.mosarts.domain.models.UserCredentials

import kotlinx.coroutines.delay
import ru.cpc.mosarts.data.models.mapToDtoe
import ru.cpc.mosarts.domain.models.RegistrateUserCredentials
import ru.cpc.mosarts.domain.repositories.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor() : ApiRepository {
    override suspend fun login(userCredentials: UserCredentials): Result<Unit> {
        val user = userCredentials.mapToDtoe()
        //delay(1000)
        return if (Math.random() > 0.5) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalAccessException(
                 "Man, you loser!")
            )
        }
    }
    
    override suspend fun registration(userCredentials: RegistrateUserCredentials): Result<Unit> {
        //delay(1000)
        return if (Math.random() > 0.5) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalAccessException(
                 "Man you loser!")
            )
        }
    }
}