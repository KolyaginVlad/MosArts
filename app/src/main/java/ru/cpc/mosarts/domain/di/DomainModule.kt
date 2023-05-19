package ru.cpc.mosarts.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.domain.usecases.LoginUseCase
import ru.cpc.mosarts.domain.usecases.LoginUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {

    @Binds
    fun LoginUseCaseImpl.bindLoginUseCase(): LoginUseCase

}