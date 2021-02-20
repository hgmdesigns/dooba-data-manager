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
import com.hgm.dooba.dataUsageStorage.UsageData
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import com.hgm.dooba.mail.SendEmail
import com.hgm.dooba.passwordManager.MyAlertDialogFragment
import com.hgm.dooba.passwordManager.PasswordListener
import com.hgm.dooba.passwordManager.PasswordManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    PasswordManager.EditNameDialogListener
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
            updateAndSendUsage()
        }

        settings.setOnClickListener {
            MyAlertDialogFragment()
                .show(supportFragmentManager, MyAlertDialogFragment.TAG)
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
    private fun updateAndSendUsage() {
        val activeUsageSession = ActiveUsageSession(this)
        val sumUsage = activeUsageSession.getActiveUsageSessionData()
        val usageDataResult = UsageData(this)
        val userDeviceInfo = GetDeviceInfo(this)
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

    override fun onFinishEditDialog(inputText: String?) {
        Toast.makeText(this, "Hi, $inputText", Toast.LENGTH_SHORT).show()
    }

}