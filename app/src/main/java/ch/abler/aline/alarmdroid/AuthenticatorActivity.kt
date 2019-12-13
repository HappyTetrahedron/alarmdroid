package ch.abler.aline.alarmdroid

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.SyncRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class AuthenticatorActivity : AccountAuthenticatorActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticator)
        accountManager = AccountManager.get(this)

        saveButton = findViewById(R.id.submit_account_button)
        nameInput = findViewById(R.id.account_name_input)
        urlInput = findViewById(R.id.url_input)

        saveButton.setOnClickListener(this)
    }

    private fun submit() {
        val name = nameInput.text.toString()
        val url = urlInput.text.toString()
        val account = Account(name, ACCOUNT_TYPE)

        val userdata = Bundle()

        userdata.putString(KEY_SYNC_URL, url)

        accountManager.addAccountExplicitly(account, null, userdata)

        ContentResolver.setIsSyncable(account, AUTHORITY, 1)
        ContentResolver.setSyncAutomatically(account, AUTHORITY, true)

        ContentResolver.requestSync(SyncRequest.Builder()
            .syncPeriodic(SYNC_INTERVAL, 600)
            .setSyncAdapter(account, AUTHORITY)
            .setExtras(Bundle.EMPTY)
            .build()
        )

        Log.d("AuthenticatorActivity", "Account added")

        setAccountAuthenticatorResult(Bundle.EMPTY)
        setResult(RESULT_OK)
        finish()
    }

    override fun onClick(v: View?) {
        submit()
    }

    companion object {
        lateinit var saveButton: Button
        lateinit var nameInput: EditText
        lateinit var urlInput: EditText

        lateinit var accountManager: AccountManager

        const val ACCOUNT_TYPE = "ch.abler.aline.alarmdroid"
        const val KEY_SYNC_URL  = "ch.abler.aline.alarmdroid.sync_url"
        const val AUTHORITY = "ch.abler.aline.alarmdroid.provider"
        const val SYNC_INTERVAL = 60L * 60 // sync every 60 minutes
    }
}