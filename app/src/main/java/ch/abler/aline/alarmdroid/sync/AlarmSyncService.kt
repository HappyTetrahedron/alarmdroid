package ch.abler.aline.alarmdroid.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmSyncService : Service() {
    override fun onCreate() {
        synchronized(syncAdapterLock) {
            syncAdapter = syncAdapter ?: AlarmSyncAdapter(applicationContext, true)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return syncAdapter!!.syncAdapterBinder
    }

    companion object {
        private var syncAdapter: AlarmSyncAdapter? = null
        private val syncAdapterLock = Any()
    }
}
