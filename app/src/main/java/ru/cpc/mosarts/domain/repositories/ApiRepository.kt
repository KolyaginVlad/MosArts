package ru.cpc.mosarts.domain.repositories


import ru.cpc.mosarts.domain.models.UserCredentials

interface ApiRepository {
    suspend fun login(userCredentials: UserCredentials): Result<Unit>
    suspend fun registration(userCredentials: UserCredentials): Result<Unit>
    
    
}