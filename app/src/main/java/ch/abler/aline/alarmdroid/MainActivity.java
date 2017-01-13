package ch.abler.aline.alarmdroid;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "ch.abler.aline.alarmdroid.provider";
    public static final String ACCOUNT_TYPE = "aline.abler.ch";
    public static final String ACCOUNT = "dummyaccount";
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = createSyncAccount(this);
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager am = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (am.addAccountExplicitly(newAccount, null, null)) {
            debug("Account added");
        }
        else {
            debug("Adding account failed");
        }
        return newAccount;
    }

    private static void debug(String msg) {
        Log.d("MainActivity", msg);
    }
}
