package com.example.chkapp

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat.startActivity


class DeviceAdminDemoReceiver : DeviceAdminReceiver() {
    private var mDevicepolicymanager: DevicePolicyManager? = null

    /** Called when this application is approved to be a device administrator.  */
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(CNAME, "onEnabled")
    }

    override fun onDisableRequested(context: Context, intent: Intent): CharSequence {
        val sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context)
        //         TODO Sending Email Service

        Log.d(CNAME, "onDisableRequested")
        return "Disabling Device Administrator means your child could change and uninstall ScreenTime."
    }

    /** Called when this application is no longer the device administrator.  */
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.d(CNAME, "onDisabled")
    }

    companion object {
        private val CNAME = DeviceAdminDemoReceiver::class.java.simpleName
        private const val SETTING_PACKAGE = "com.android.settings"

        /* Allow other admins to change the password again without entering it. */
        private const val RESET_PASSWORD_NOT_REQUIRE_ENTRY = 0
        private const val RESET_PASSWORD_TIME_OUT = 10000
        private const val TEMP_PASSWORD = "1"
    }

}