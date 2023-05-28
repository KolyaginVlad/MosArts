package ru.cpc.mosarts.utils.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cpc.mosarts.utils.log.Logger
import ru.cpc.mosarts.utils.log.LoggerImpl

@InstallIn(SingletonComponent::class)
@Module
interface UtilsModule {

    @Binds
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}