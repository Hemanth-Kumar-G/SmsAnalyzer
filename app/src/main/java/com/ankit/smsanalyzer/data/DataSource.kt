package com.ankit.smsanalyzer.data

interface DataSource{
    fun getSmsManager(): SmsManager
}