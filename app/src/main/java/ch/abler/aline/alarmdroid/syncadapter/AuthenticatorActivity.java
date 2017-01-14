package ch.abler.aline.alarmdroid.syncadapter;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import ch.abler.aline.alarmdroid.R;

public class AuthenticatorActivity extends AccountAuthenticatorActivity implements View.OnClickListener{

    public static String EXTRA_ACC_TYPE = "acctype";
    public static String EXTRA_AUTH_TYPE = "authtype";
    public static String EXTRA_IS_NEW_ACC = "isnewacc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        Button btn = (Button) findViewById(R.id.accountSubmitButton);
        btn.setOnClickListener(this);
        debug(btn.getText().toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    private void submit() {
        debug("submitting acc data");
        TextView serverAddress = (TextView) findViewById(R.id.serverAddress);
        TextView serverPort = (TextView) findViewById(R.id.serverPort);
        TextView accountName = (TextView) findViewById(R.id.accountName);
        final String address = serverAddress.getText().toString();
        final String port = serverPort.getText().toString();
        final String name = accountName.getText().toString();

        final Account account = new Account(name, Constants.ACCOUNT_TYPE);

        AccountManager am = AccountManager.get(this);

        Bundle userdata = new Bundle();

        userdata.putString(Constants.KEY_SERVER_ADDR, address);
        userdata.putString(Constants.KEY_SERVER_PORT, port);

        am.addAccountExplicitly(account,null,userdata);


        // set up account syncing
        ContentResolver.addPeriodicSync(account, Constants.AUTHORITY, Bundle.EMPTY, Constants.SYNC_INTERVAL);

        setAccountAuthenticatorResult(null);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View view) {
        submit();
    }

    private void debug(String msg){
        Log.d("AuthenticatorActivity", msg);
    }
}
