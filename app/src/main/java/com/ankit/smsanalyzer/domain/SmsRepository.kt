package com.ankit.smsanalyzer.domain

import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

interface SmsRepository {
    fun getSmsList(): Single<List<SmsItem>>
}