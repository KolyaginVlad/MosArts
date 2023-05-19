package ru.cpc.mosarts.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.data.repositories.ApiRepositoryImpl
import ru.cpc.mosarts.domain.repositories.ApiRepository


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    //Тут Binds
    @Binds
    fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl,
    ): ApiRepository
    companion object {
        //Тут Provides
    }
}