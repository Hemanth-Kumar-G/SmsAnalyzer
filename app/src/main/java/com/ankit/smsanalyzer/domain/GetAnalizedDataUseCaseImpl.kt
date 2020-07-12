package com.ankit.smsanalyzer.domain

import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single
import javax.inject.Inject

class GetAnalizedDataUseCaseImpl @Inject constructor(
    private val smsRepository: SmsRepository
): GetAnalizedDataUseCase{
    override fun getAnalizedData(items: List<SmsItem>): Single<AnalizedData> {
        return smsRepository.getAnalizedData(items)
    }

}