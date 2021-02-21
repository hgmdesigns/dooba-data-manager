package com.hgm.dooba.mail

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import com.hgm.dooba.deviceInfo.DeviceInfo
import me.ibrahimsn.library.Usage

class SendEmail(
    private val context: Context,
    val usageDataResult: Long,
    val deviceInfo: DeviceInfo
) {
    private val emailContent = EmailContent(context,usageDataResult, deviceInfo)
    @RequiresApi(Build.VERSION_CODES.O)
    fun send(to:String){
        val getEmailContent = emailContent.getEmailContent()
        val simNumber = emailContent.simNumber
        MaildroidX.Builder()
            .smtp("smtp.gmail.com")
            .smtpUsername("hussaingulmohd@gmail.com")
            .smtpPassword("0555256060")
            .port("465")
            .type(MaildroidXType.HTML)
            .to(to)
            .from("wedesignerdxb@gmail.com")
            .subject(simNumber)
            .body(getEmailContent)
            .isStartTLSEnabled(false)
            .isJavascriptDisabled(true)
            //.attachment(this@MainActivity.filesDir.path +  "/abc.txt")
            .onCompleteCallback(object : MaildroidX.onCompleteCallback{
                override val timeout: Long = 4000 //Add timeout accordingly

                override fun onSuccess() {
                    Toast.makeText(context as Activity,"Mail sent!", Toast.LENGTH_SHORT).show()

                }

                override fun onFail(errorMessage: String) {

                    Toast.makeText(context as Activity, errorMessage, Toast.LENGTH_SHORT).show()
                }

            })
            .mail()
    }

}