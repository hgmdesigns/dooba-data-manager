package com.hgm.dooba

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.format.Formatter.formatShortFileSize
import android.util.Log
import androidx.annotation.RequiresApi
import com.hgm.dooba.DataUsageStorage.ActiveUsageSession
import com.hgm.dooba.DataUsageStorage.UsageData
import com.hgm.dooba.mail.SendEmail
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enablePermission.setOnClickListener{
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        sendMail.setOnClickListener{
            updateAndSendUsage()
        }
        applyForPermission()
        val callLog = ActiveUsageSession(this)
        callLog.logData()
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
    private fun updateAndSendUsage() {
        val usageDataResult = UsageData(this)
        val usageDataOutput = usageDataResult.dataUsageResult()
        val userDeviceInfo = GetDeviceInfo(this)
        val sumUsage = usageDataOutput.downloads + usageDataOutput.uploads
        val dataLimit = 24
        usage.text = (formatShortFileSize(this,sumUsage)).toString() + " /" + dataLimit + " GB"
        SendEmail(
            this,
            usageDataResult.dataUsageResult(),
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