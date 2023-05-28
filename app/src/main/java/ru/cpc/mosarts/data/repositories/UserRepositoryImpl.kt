package ru.cpc.mosarts.data.repositories

import ru.cpc.mosarts.data.models.mapToDtoe
import ru.cpc.mosarts.domain.models.MoreInf
import ru.cpc.mosarts.domain.models.UserCredentials
import ru.cpc.mosarts.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun login(userCredentials: UserCredentials): Result<Unit> {
        val user = userCredentials.mapToDtoe()
        return Result.success(Unit)
    }

    override suspend fun registration(userCredentials: UserCredentials): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun moreInf(moreInf: MoreInf): Result<Unit> {
        return Result.success(Unit)
    }

}