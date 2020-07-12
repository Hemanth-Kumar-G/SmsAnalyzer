package com.ankit.smsanalyzer.domain

import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

interface GetAnalizedDataUseCase {
    fun getAnalizedData(items: List<SmsItem>): Single<AnalizedData>
}