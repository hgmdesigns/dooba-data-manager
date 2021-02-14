package com.hgm.dooba.DataUsageStorage

import android.content.Context
import android.util.Log

class ActiveUsageSession(private val context: Context) {
    val usageDataResult = UsageData(context)
    val usageDataOutput = usageDataResult.dataUsageResult()

    val downloads = usageDataOutput.downloads
    val uploads = usageDataOutput.uploads
    private val finalDataUsage = downloads + uploads
    val existingUsage = finalDataUsage
    fun getActiveUsageSessionData(): Long {
        return finalDataUsage - existingUsage
    }
    fun logData() {
        Log.d('m'.toString(), "LOGOUTPUT ${getActiveUsageSessionData()}")
    }
    //TODO STORE EXISTING USAGE IN SQL AND GET IT FROM THERE
    // ALSO USE THIS CLASS EVERYWHERE TO GET FINAL DATA
}