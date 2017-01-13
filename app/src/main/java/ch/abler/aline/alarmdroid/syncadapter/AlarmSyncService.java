package ch.abler.aline.alarmdroid.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AlarmSyncService extends Service {

    private static AlarmSyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new AlarmSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
