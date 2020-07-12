package com.ankit.smsanalyzer.data

import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val smsManager: SmsManager) : DataSource {
    override fun getSmsManager(): SmsManager {
        return smsManager
    }
}