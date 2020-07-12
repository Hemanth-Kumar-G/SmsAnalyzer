package com.ankit.smsanalyzer.presentation

data class SmsItem(
    val smsId: String,
    val smsTitle: String,
    val content: String,
    var isTagged: Boolean = false,
    var isCredited: Boolean = false
)