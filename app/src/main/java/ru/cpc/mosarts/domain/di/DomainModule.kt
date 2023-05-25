package ru.cpc.mosarts.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.domain.usecases.GetListOfSchoolsUseCase
import ru.cpc.mosarts.domain.usecases.GetListOfSchoolsUseCaseImpl
import ru.cpc.mosarts.domain.usecases.GetThemesUseCase
import ru.cpc.mosarts.domain.usecases.GetThemesUseCaseImpl
import ru.cpc.mosarts.domain.usecases.GetVkProfileInfoUseCase
import ru.cpc.mosarts.domain.usecases.GetVkProfileInfoUseCaseImpl
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
import ru.cpc.mosarts.domain.usecases.SaveTokenUseCase
import ru.cpc.mosarts.domain.usecases.SaveTokenUseCaseImpl
import ru.cpc.mosarts.domain.usecases.SendSelectedThemesUseCase
import ru.cpc.mosarts.domain.usecases.SendSelectedThemesUseCaseImpl

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
    fun SaveTokenUseCaseImpl.bindSaveTokenUseCase(): SaveTokenUseCase

    @Binds
    fun GetVkProfileInfoUseCaseImpl.bindGetVkProfileInfoUseCase(): GetVkProfileInfoUseCase

	@Binds
	fun GetThemesUseCaseImpl.bindGetThemesUseCase(): GetThemesUseCase


	@Binds
	fun SendSelectedThemesUseCaseImpl.bindSendSelectedThemesUseCase(): SendSelectedThemesUseCase

	@Binds
	fun GetListOfSchoolsUseCaseImpl.bindGetListOfSchoolsUseCase(): GetListOfSchoolsUseCase

	@Binds
	fun GetSimpleTestUseCaseImpl.bindGetSimpleTestUseCase(): GetSimpleTestUseCase

	@Binds
	fun SendSimpleTestUseCaseImpl.bindSendSimpleTestUseCase(): SendSimpleTestUseCase
}