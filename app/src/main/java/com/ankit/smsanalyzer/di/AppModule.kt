package com.ankit.smsanalyzer.di

import android.content.Context
import com.ankit.smsanalyzer.data.*
import com.ankit.smsanalyzer.domain.SmsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSmsManager(@ApplicationContext context: Context):SmsManager{
        return SmsManagerImpl(context)
    }

    @Singleton
    @Provides
    fun provideDataSource(smsManager: SmsManager):DataSource{
        return DataSourceImpl(smsManager)
    }
    @Singleton
    @Provides
    fun provideSmsRepository(dataSource: DataSource): SmsRepository {
        return SmsRepositoryImpl(dataSource)
    }
}