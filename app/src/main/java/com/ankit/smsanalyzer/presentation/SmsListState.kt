package com.ankit.smsanalyzer.presentation

sealed class SmsListState {
    class Success(val items: List<SmsItem>): SmsListState()
    class Failed(val trowable: Throwable): SmsListState()
}