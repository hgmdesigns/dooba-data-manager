package com.hgm.dooba.dataUsageStorage

import android.content.Context
import android.util.Log

class ActiveUsageSession(private val context: Context) {
    val usageDataResult = UsageData(context)
    val usageDataOutput = usageDataResult.dataUsageResult()

    val downloads = usageDataOutput.downloads
    val uploads = usageDataOutput.uploads
    private val finalDataUsage = downloads + uploads
    val existingUsage = finalDataUsage

    val usageStorage = UsageStorage(context)

    fun storeCurrentExistingUsageData() {
        usageStorage.writeFileOnInternalStorage("existingUsage", existingUsage.toString())
    }

    fun getActiveUsageSessionData(): Long {
       val storeExistingUsageData = usageStorage.readFileOnInternalStorage("existingUsage").toLong()
        return finalDataUsage - storeExistingUsageData
    }
    fun logData() {
        Log.d('m'.toString(), "LOGOUTPUT ${getActiveUsageSessionData()}")
    }

}