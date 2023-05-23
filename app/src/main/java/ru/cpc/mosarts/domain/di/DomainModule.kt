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
import ru.cpc.mosarts.domain.usecases.GetSimpleTestUseCase
import ru.cpc.mosarts.domain.usecases.GetSimpleTestUseCaseImpl
import ru.cpc.mosarts.domain.usecases.SendSimpleTestUseCase
import ru.cpc.mosarts.domain.usecases.SendSimpleTestUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {
	@Binds
	fun LoginUseCaseImpl.bindLoginUseCase(): LoginUseCase
	
	@Binds
	fun RegistrationUseCaseImpl.bindRegistrationUseCase(): RegistrationUseCase
	
	@Binds
	fun MoreInfUseCaseImpl.bindMoreInfUseCase(): MoreInfUseCase
	
	@Binds
	fun GetSimpleTestUseCaseImpl.bindGetSimpleTestUseCase(): GetSimpleTestUseCase
	
	@Binds
	fun SendSimpleTestUseCaseImpl.bindSendSimpleTestUseCase(): SendSimpleTestUseCase
}