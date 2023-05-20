package ru.cpc.mosarts.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.data.repositories.TestRepositoryImpl
import ru.cpc.mosarts.data.repositories.UserRepositoryImpl
import ru.cpc.mosarts.domain.repositories.TestRepository
import ru.cpc.mosarts.domain.repositories.UserRepository


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
	//Тут Binds
	@Binds
	fun UserRepositoryImpl.bindApiRepository(): UserRepository
	
	@Binds
	fun TestRepositoryImpl.bindTestRepository(): TestRepository
	
	companion object {
		//Тут Provides
	}
}