package com.ankit.smsanalyzer.data

import com.ankit.smsanalyzer.domain.SmsRepository
import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

class SmsRepositoryImpl(private val dataSource: DataSource) : SmsRepository {
    override fun getSmsList(): Single<List<SmsItem>> {
        return dataSource.getSmsManager().getSmsList()
    }

    override fun getAnalizedData(items: List<SmsItem>): Single<AnalizedData> {
        return dataSource.getSmsManager().getAnalizedData(items)
    }
}