package com.example.chkapp

import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private val CNAME = MainActivity::class.java.simpleName

    private var toggleButton: ToggleButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton = super.findViewById<View>(R.id.toggle_device_admin) as ToggleButton
        init()
    }

    fun init() {

        DeviceAdminUtil.initDPM(this)
        DeviceAdminUtil.initComponent(this, DeviceAdminDemoReceiver::class.java)
        val admin = DeviceAdminUtil.isDeviceAdmin()
        Log.i(CNAME, "admin : $admin")
        toggleButton!!.isChecked = admin
        toggleButton!!.setOnCheckedChangeListener(this)
    }


    override fun onCheckedChanged(button: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            if (DeviceAdminUtil.isDeviceAdmin()) return

            // Activate device administration
            DeviceAdminUtil.registerDeviceAdmin(this, DeviceAdminUtil.DEVICE_ADMIN_REQUEST)
        } else {
            DeviceAdminUtil.unregisterDeviceAdmin()
            Log.i(CNAME, "Device Admin Disabled")
            Toast.makeText(this, "Device Admin Disabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DeviceAdminUtil.DEVICE_ADMIN_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    Log.i(CNAME, "Administration enabled!")
                    Toast.makeText(this, "Administration enabled!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i(CNAME, "Administration enable FAILED!")
                    toggleButton!!.isChecked = false
                }
                return
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}