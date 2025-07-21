package com.anderson.nutritionapp.di

import android.app.Application
import com.anderson.nutritionapp.data.manager.LocalUserManagerImpl
import com.anderson.nutritionapp.domain.manager.LocalUserManager
import com.anderson.nutritionapp.domain.usecase.AppEntryUseCases
import com.anderson.nutritionapp.domain.usecase.ReadAppEntry
import com.anderson.nutritionapp.domain.usecase.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application : Application
    ): LocalUserManager  = LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCase(localUserManager: LocalUserManager)= AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )
}