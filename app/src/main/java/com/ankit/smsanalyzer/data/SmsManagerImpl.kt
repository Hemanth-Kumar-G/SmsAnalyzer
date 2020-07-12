package com.ankit.smsanalyzer.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import com.ankit.smsanalyzer.presentation.SmsItem
import io.reactivex.Single

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
                        if(body.contains("credited"))
                            list.add(SmsItem(smsId = id, smsTitle = number?:"", content = body))
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
}