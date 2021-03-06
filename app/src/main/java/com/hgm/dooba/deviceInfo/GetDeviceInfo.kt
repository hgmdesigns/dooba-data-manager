package com.hgm.dooba.deviceInfo

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.text.Editable
import androidx.annotation.RequiresApi
import com.hgm.dooba.dataUsageStorage.UsageStorage


class GetDeviceInfo(private val context: Context) {
    val fileStorage = UsageStorage(context)
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    @RequiresApi(Build.VERSION_CODES.O)
    val IMEI: String = tm.imei
    val simNumber: String = tm.simSerialNumber

    fun getDeviceInfo(): DeviceInfo {
        DeviceInfo.Imei = IMEI
        DeviceInfo.simNumber = simNumber
        return DeviceInfo
    }

//    fun setSimNumber(updatedSimNumber: Editable?) {
//        fileStorage.writeFileOnInternalStorage("Sim Number", updatedSimNumber.toString())
//        DeviceInfo.simNumber = updatedSimNumber.toString()
//    }
//
//    fun getSimNumber(): String {
//        DeviceInfo.simNumber = simNumber
//        return
//    }
}