package ch.abler.aline.alarmdroid.sync

import android.accounts.Account
import android.accounts.AccountManager
import android.app.AlarmManager
import android.app.Service
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.util.Log
import ch.abler.aline.alarmdroid.AuthenticatorActivity
import okhttp3.*
import java.lang.Exception

class AlarmSyncAdapter @JvmOverloads constructor(
    context: Context?,
    autoInitialize: Boolean,
    allowParallelSyncs: Boolean = false
) : AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

    private val accountManager = AccountManager.get(getContext())
    private val alarmManager = getContext().getSystemService(Service.ALARM_SERVICE) as AlarmManager

    private val httpClient = OkHttpClient()

    override fun onPerformSync(
        account: Account?,
        extras: Bundle?,
        authority: String?,
        provider: ContentProviderClient?,
        syncResult: SyncResult?
    ) {
        val url = accountManager.getUserData(account, AuthenticatorActivity.KEY_SYNC_URL)
        val alarmClockInfo = alarmManager.nextAlarmClock

        val data = alarmClockInfo?.triggerTime?.toString()
        val payload = if (data != null) {
            "{\"next_alarm\":$data}"
        }
        else {
            "{}"
        }

        try {
            val request = Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.get("application/json"), payload))
                .build()
            val response = httpClient.newCall(request).execute()
            Log.d("AlarmSyncAdapter", response.message())
        }
        catch (e: Exception) {
            Log.e("AlarmSyncAdapter", e.message, e)
        }

    }

}
