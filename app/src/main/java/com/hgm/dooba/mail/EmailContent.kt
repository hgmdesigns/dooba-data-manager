package com.hgm.dooba.mail

import android.content.Context
import android.os.Build
import android.text.format.Formatter
import androidx.annotation.RequiresApi
import com.hgm.dooba.deviceInfo.DeviceInfo
import me.ibrahimsn.library.Usage

class EmailContent(
    private val context: Context,
    usage: Long,
    deviceInfo: DeviceInfo
) {
    val sumData = usage
    val Imei = deviceInfo.Imei
    val simNumber = deviceInfo.simNumber

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEmailContent(): String {
        return """
            <h1>${Formatter.formatShortFileSize(context, sumData)}</h1>
            <h4>IMEI ${Imei}</h4>
             <h4>Sim Number ${simNumber}</h4>
        """.trimIndent()
    }
}