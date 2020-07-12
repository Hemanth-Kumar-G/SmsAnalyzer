package com.ankit.smsanalyzer.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single
import java.util.regex.Pattern

private const val CREATED = "credited"
private const val DEBITED = "debited"

class SmsManagerImpl(private val context: Context) : SmsManager {

    @SuppressLint("Recycle")
    override fun getSmsList(): Single<List<SmsItem>> {
        return Single.create { emitter ->
            val contentResolver: ContentResolver = context.applicationContext.contentResolver
            val selection = "${Telephony.Sms.TYPE} = ?"
            val selectionArgs = arrayOf(Telephony.Sms.MESSAGE_TYPE_INBOX.toString())
            val cursor: Cursor? =
                contentResolver.query(
                    Telephony.Sms.CONTENT_URI,
                    null,
                    selection,
                    selectionArgs,
                    null
                )
            val list = arrayListOf<SmsItem>()
            cursor?.let {
                try {
                    it.moveToFirst()
                    while (!it.isAfterLast) {
                        // val smsDate = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.DATE))
                        val number = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                        val body = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.BODY))
                        val id = it.getString(it.getColumnIndexOrThrow(Telephony.Sms._ID))
                        if (body.contains(CREATED))
                            list.add(
                                SmsItem(
                                    smsId = id,
                                    smsTitle = number ?: "",
                                    content = body,
                                    isCredited = true
                                )
                            )
                        else if (body.contains(DEBITED)) {
                            list.add(SmsItem(smsId = id, smsTitle = number ?: "", content = body))
                        }
                        it.moveToNext()
                    }
                    emitter.onSuccess(list)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.onError(Throwable("Failed to fetch sms list: " + e.localizedMessage))
                } finally {
                    it.close()
                }

            }
        }
    }

    override fun getAnalizedData(items: List<SmsItem>): Single<AnalizedData> {
        return Single.create<AnalizedData> {
            var totalCreditedIncome: Double = 0.0
            var totalCreditedExpences: Double = 0.0
            for (smsItem in items) {
                if (smsItem.isCredited) { //credited
                    val p: Pattern = Pattern.compile("\\d+")
                    val result = p.toRegex()
                        .find(smsItem.content.substring(0, smsItem.content.indexOf(CREATED)), 0)
                    Log.d(CREATED, "${result?.value}")
                    result?.let {
                        totalCreditedIncome += it.value.toDouble()
                    }
                } else { //dibited
                    val p: Pattern = Pattern.compile("\\d+")
                    val result = p.toRegex()
                        .find(smsItem.content.substring(0, smsItem.content.indexOf(DEBITED)), 0)
                    Log.d(DEBITED, "${result?.value}")
                    result?.let {
                        totalCreditedExpences += it.value.toDouble()
                    }
                }
            }

            it.onSuccess(AnalizedData(totalCreditedIncome, totalCreditedExpences))
        }
    }
}