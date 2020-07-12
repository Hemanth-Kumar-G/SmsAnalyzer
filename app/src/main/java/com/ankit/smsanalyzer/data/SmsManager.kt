package com.ankit.smsanalyzer.data

import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

interface SmsManager {
    fun getSmsList(): Single<List<SmsItem>>

    fun getAnalizedData(items: List<SmsItem>) : Single<AnalizedData>
}