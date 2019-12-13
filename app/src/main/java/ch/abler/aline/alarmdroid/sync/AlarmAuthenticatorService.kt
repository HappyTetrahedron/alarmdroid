package ch.abler.aline.alarmdroid.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmAuthenticatorService: Service() {
    override fun onCreate() {
        synchronized(authenticatorLock) {
            authenticator = authenticator ?: AlarmAuthenticator(applicationContext)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return authenticator!!.iBinder
    }

    companion object {
        private var authenticator: AlarmAuthenticator? = null
        private val authenticatorLock = Any()
    }

}