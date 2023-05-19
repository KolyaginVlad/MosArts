package ru.cpc.mosarts.utils.di

import ru.cpc.mosarts.utils.log.Logger
import ru.cpc.mosarts.utils.log.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UtilsModule {

    @Binds
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}