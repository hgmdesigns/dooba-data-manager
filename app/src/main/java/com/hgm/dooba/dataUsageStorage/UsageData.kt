package com.hgm.dooba.dataUsageStorage

import android.app.usage.NetworkStatsManager
import android.content.Context
import android.telephony.TelephonyManager
import me.ibrahimsn.library.DataUsageManager
import me.ibrahimsn.library.Interval
import me.ibrahimsn.library.NetworkType
import me.ibrahimsn.library.Usage

class UsageData(private val context: Context) {
    private val networkStatsManager = context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    private val manager = DataUsageManager(networkStatsManager, telephonyManager.subscriberId)
    // Monitor single interval
    fun dataUsageResult(): Usage {
        return manager.getUsage(Interval.last30days, NetworkType.MOBILE)
    }

}