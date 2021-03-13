package com.hgm.dooba.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import io.karn.notify.Notify

class AutoNotifyWorker(val context: Context, params: WorkerParameters): Worker(context, params) {
    private val activeUsageSession = ActiveUsageSession(this.applicationContext)
    private val sumUsage = activeUsageSession.getActiveUsageSessionData()
    private val sumUsageLimit = activeUsageSession.getUsageLimit()

    override fun doWork(): Result {
        Log.i("WORKER", "$sumUsage")
       return Result.success()
    }
}