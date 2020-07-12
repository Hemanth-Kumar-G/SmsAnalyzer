package com.ankit.smsanalyzer.data

import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

interface SmsManager {
    fun getSmsList(): Single<List<SmsItem>>
}