package ch.abler.aline.alarmdroid.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.Service;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
            sock = new DatagramSocket(0);
        }
        catch (SocketException e) {
            debug("Error during socket creation: " + e.getMessage());
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        String data;
        String serverAddr;
        int serverPort;
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Service.ALARM_SERVICE);

        // get next alarm
        AlarmManager.AlarmClockInfo clockInfo = alarmManager.getNextAlarmClock();
        if (clockInfo != null) {
            data = Long.toString(clockInfo.getTriggerTime());
        }
        else data = "none";

        // get account data
        AccountManager accountManager = AccountManager.get(getContext());
        serverAddr = accountManager.getUserData(account, Constants.KEY_SERVER_ADDR);
        serverPort = Integer.parseInt(accountManager.getUserData(account, Constants.KEY_SERVER_PORT));

        // send alarm data
        try {
            InetAddress inetAddr = InetAddress.getByName(serverAddr);
            byte[] outData = data.getBytes();

            sock.send(
                    new DatagramPacket( outData, outData.length, inetAddr, serverPort)
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
