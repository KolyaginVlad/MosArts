package ru.cpc.mosarts.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.data.repositories.UserRepositoryImpl
import ru.cpc.mosarts.domain.repositories.UserRepository


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    //Тут Binds
    @Binds
    fun bindApiRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository
    companion object {
        //Тут Provides
    }
}