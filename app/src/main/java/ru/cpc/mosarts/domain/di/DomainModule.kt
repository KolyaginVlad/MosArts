package ru.cpc.mosarts.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.domain.usecases.LoginUseCase
import ru.cpc.mosarts.domain.usecases.LoginUseCaseImpl
import ru.cpc.mosarts.domain.usecases.MoreInfUseCase
import ru.cpc.mosarts.domain.usecases.MoreInfUseCaseImpl
import ru.cpc.mosarts.domain.usecases.RegistrationUseCase
import ru.cpc.mosarts.domain.usecases.RegistrationUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {
	@Binds
	fun LoginUseCaseImpl.bindLoginUseCase(): LoginUseCase
	
	@Binds
	fun RegistrationUseCaseImpl.bindRegistrationUseCase(): RegistrationUseCase
	
	@Binds
	fun MoreInfUseCaseImpl.bindMoreInfUseCase(): MoreInfUseCase
}