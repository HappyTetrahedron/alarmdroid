package ch.abler.aline.alarmdroid.syncadapter;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmSyncAdapter extends AbstractThreadedSyncAdapter {
    DatagramSocket sock;

    public AlarmSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        setUpSock();
    }

    public AlarmSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        setUpSock();
    }

    void setUpSock() {
        try {
            sock = new DatagramSocket(7887);
        }
        catch (SocketException e) {
            debug("Error during socket creation: " + e.getMessage());
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        String data;
        try {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Service.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AlarmManager.AlarmClockInfo clockInfo = alarmManager.getNextAlarmClock();
                if (clockInfo != null) {
                    data = Long.toString(clockInfo.getTriggerTime());
                }
                else data = "none";
            } else {
                data = "none";
            }
        }
        catch (Exception e) {
            data = e.getMessage();
        }
        try {

            InetAddress inetAddr = InetAddress.getByName("192.168.0.114");
            byte[] outData = data.getBytes();

            sock.send(
                    new DatagramPacket( outData, outData.length, inetAddr, 7887)
            );
        }
        catch (UnknownHostException e) {
            debug (e.getMessage());
        }
        catch (IOException e) {
            debug ("Error sending packet: " + e.getMessage());
        }



    }

    private void debug(String msg) {
        Log.d("AlarmSyncAdapter", msg);
    }
}
