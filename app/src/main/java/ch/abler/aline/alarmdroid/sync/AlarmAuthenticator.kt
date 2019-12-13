package ch.abler.aline.alarmdroid.sync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import ch.abler.aline.alarmdroid.AuthenticatorActivity

class AlarmAuthenticator(private val context: Context) : AbstractAccountAuthenticator(context) {

    override fun getAuthTokenLabel(authTokenType: String?): String = "ch.abler.aline.alarmdroid"

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle = Bundle.EMPTY

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle = Bundle.EMPTY

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle = Bundle.EMPTY

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle = Bundle.EMPTY

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle = Bundle.EMPTY

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        val intent = Intent(context, AuthenticatorActivity::class.java)
        intent.putExtra(EXTRA_ACCOUNT_TYPE, accountType)
        intent.putExtra(EXTRA_AUTH_TYPE, authTokenType)
        intent.putExtra(EXTRA_IS_NEW_ACCOUNT, true)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    companion object {
        const val EXTRA_ACCOUNT_TYPE = "ex_acc_type"
        const val EXTRA_AUTH_TYPE = "ex_auth_type"
        const val EXTRA_IS_NEW_ACCOUNT = "ex_is_new_acc"
    }

}