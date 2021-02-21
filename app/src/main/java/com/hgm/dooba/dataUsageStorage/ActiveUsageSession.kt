package com.hgm.dooba.dataUsageStorage

import android.content.Context
import android.text.Editable
import android.util.Log
import android.widget.Toast

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

    fun storeUsageLimit(usageLimit: Editable?){
        usageStorage.writeFileOnInternalStorage("usageLimit", usageLimit.toString())
    }

    fun getUsageLimit(): Long {
        var usageLimit: Long = 20
         try {
             usageLimit = usageStorage.readFileOnInternalStorage("usageLimit").toLong()
        } catch (e: Exception) {
            Toast.makeText(context, "Set Data Limit from settings!", Toast.LENGTH_SHORT).show()
        }
        return usageLimit
    }
    fun logData() {
        Log.d('m'.toString(), "LOGOUTPUT ${getActiveUsageSessionData()}")
    }

}