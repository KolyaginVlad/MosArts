package ru.cpc.mosarts.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    //Тут Binds
    companion object {
        //Тут Provides
    }
}