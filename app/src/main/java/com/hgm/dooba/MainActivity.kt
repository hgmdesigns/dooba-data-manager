package com.hgm.dooba

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.format.Formatter.formatShortFileSize
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import com.hgm.dooba.mail.SendEmail
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settingsActivity = com.hgm.dooba.Settings()

        enablePermission.setOnClickListener{
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        sendMail.setOnClickListener{
            sendMail()
        }

        getUsage.setOnClickListener {
            updateUsage()
        }

        settings.setOnClickListener {
            startActivity(Intent(this, settingsActivity.javaClass))
        }

        applyForPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()) {
            for(i in grantResults.indices) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionRequest", "${permissions[i]} granted!")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUsage() {
        val activeUsageSession = ActiveUsageSession(this)
        val sumUsage = activeUsageSession.getActiveUsageSessionData()
        val dataLimit = activeUsageSession.getUsageLimit()
        if (dataLimit.toString().isEmpty() && sumUsage.toString().isEmpty()) {
            Toast.makeText(this, "Please set Sim Number & Data Limit first!", Toast.LENGTH_LONG).show()
        } else {
            usage.text = (formatShortFileSize(this,sumUsage)).toString() + " /" + dataLimit + " GB"
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMail() {
        val activeUsageSession = ActiveUsageSession(this)
        val sumUsage = activeUsageSession.getActiveUsageSessionData()
        val userDeviceInfo = GetDeviceInfo(this)

        SendEmail(
            this,
            sumUsage,
            userDeviceInfo.getDeviceInfo()

        )
            .send("hassangm@pm.me")
    }

    private fun applyForPermission() {
        val applyForPermission = GetPermission(this)

        applyForPermission.hasPhoneStatePermission()
        applyForPermission.hasPackageUsagePermission()
        applyForPermission.requestPermission()
    }


}