package com.hgm.dooba.mail

import android.content.Context
import android.os.Build
import android.text.format.Formatter
import android.util.Log
import androidx.annotation.RequiresApi
import com.hgm.dooba.DeviceInfo
import me.ibrahimsn.library.Usage

class EmailContent(
    private val context: Context,
    usage: Usage,
    deviceInfo: DeviceInfo
) {
    private val upload = usage.uploads
    private val download = usage.downloads
    private val sumData = upload + download
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