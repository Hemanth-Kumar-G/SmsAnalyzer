package com.ankit.smsanalyzer.di

import com.ankit.smsanalyzer.domain.GetSmsListUseCase
import com.ankit.smsanalyzer.domain.GetSmsListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class UseCaseModule {
    @Provides
    fun provideSmsListUseCase(smsListUseCaseImpl: GetSmsListUseCaseImpl):GetSmsListUseCase{
        return smsListUseCaseImpl
    }
}