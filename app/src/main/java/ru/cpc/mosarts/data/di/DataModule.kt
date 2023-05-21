package ru.cpc.mosarts.data.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.data.repositories.TestRepositoryImpl
import ru.cpc.mosarts.data.repositories.VkRepositoryImpl
import ru.cpc.mosarts.domain.repositories.VkRepository
import ru.cpc.mosarts.data.repositories.UserRepositoryImpl
import ru.cpc.mosarts.domain.repositories.TestRepository
import ru.cpc.mosarts.domain.repositories.UserRepository


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun UserRepositoryImpl.bindUserRepository(): UserRepository

    @Binds
    fun VkRepositoryImpl.bindVkRepository(): VkRepository

    @Binds
    fun TestRepositoryImpl.bindTestRepository(): TestRepository


    companion object {
        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences("MoscowArts", MODE_PRIVATE)
        }
    }
}