package com.ankit.smsanalyzer.domain

import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single
import javax.inject.Inject

class GetSmsListUseCaseImpl @Inject constructor(private val smsRepository: SmsRepository) :
    GetSmsListUseCase {
    override fun getSmsList(): Single<List<SmsItem>> {
        return smsRepository.getSmsList()
    }
}