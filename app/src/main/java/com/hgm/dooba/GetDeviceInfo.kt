package com.hgm.dooba

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi


class GetDeviceInfo(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDeviceInfo(): DeviceInfo {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val IMEI: String = tm.imei
        val simNumber: String = tm.simSerialNumber

        val deviceInfo = DeviceInfo()
        deviceInfo.Imei = IMEI
        deviceInfo.simNumber = simNumber

        return deviceInfo

    }
}