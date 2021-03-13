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
import androidx.work.*
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import com.hgm.dooba.mail.SendEmail
import com.hgm.dooba.worker.AutoMailWorker
import com.hgm.dooba.worker.AutoNotifyWorker
import com.hgm.dooba.worker.CheckUsageWorkerManager
import io.karn.notify.Notify
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

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
            startWorker()
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
            usage.text = (formatShortFileSize(this,sumUsage)).toString() + " /" + (dataLimit / sumUsage)*100 + " GB"
            savedUsage.text = (formatShortFileSize(this,activeUsageSession.existingUsage.toLong())).toString()
        }

    }



    private fun applyForPermission() {
        val applyForPermission = GetPermission(this)

        applyForPermission.hasPhoneStatePermission()
        applyForPermission.hasPackageUsagePermission()
        applyForPermission.requestPermission()
    }

    // Constrainsts for Worker
    fun createConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)  // if connected to Internet
        .build()

    // Worker Request
    fun createWorkRequest() = PeriodicWorkRequestBuilder<AutoNotifyWorker>(15, TimeUnit.MINUTES)  // setting period to 15 Minutes
        .setConstraints(createConstraints())
        // setting a backoff on case the work needs to retry
        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
        .build()

    // Enqueue Worker
    fun startWorker() {
        WorkManager.getInstance().enqueueUniquePeriodicWork("Check", ExistingPeriodicWorkPolicy.REPLACE, createWorkRequest())
    }




}