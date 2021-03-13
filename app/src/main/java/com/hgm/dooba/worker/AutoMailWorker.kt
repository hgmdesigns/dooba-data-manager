package com.hgm.dooba.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import com.hgm.dooba.mail.SendEmail

class AutoMailWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        sendMail()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMail() {
        val activeUsageSession = ActiveUsageSession(this.applicationContext)
        val sumUsage = activeUsageSession.getActiveUsageSessionData()
        val userDeviceInfo = GetDeviceInfo(this.applicationContext)

        SendEmail(
            this.applicationContext,
            sumUsage,
            userDeviceInfo.getDeviceInfo()

        )
            .send("hassangm@pm.me")
    }

}