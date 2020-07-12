package com.ankit.smsanalyzer.presentation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainModule {
    @ActivityScoped
    @Provides
    fun provideSmsList(): ArrayList<SmsItem> {
        return ArrayList<SmsItem>().apply{
            add(SmsItem("","Hello","How are you."))
            add(SmsItem("","Hello","How are you."))
            add(SmsItem("","Hello","How are you."))
            add(SmsItem("","Hello","How are you."))
            add(SmsItem("","Hello","How are you."))
        }
    }

    @ActivityScoped
    @Provides
    fun provideSmsAdapter(items: ArrayList<SmsItem>): SmsItemAdapter {
        return SmsItemAdapter(items)
    }
}