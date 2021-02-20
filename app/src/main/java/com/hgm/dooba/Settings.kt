package com.hgm.dooba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import kotlinx.android.synthetic.main.activity_settings.*


class Settings : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val activeUsageSession = ActiveUsageSession(this)
        val deviceInfo = GetDeviceInfo(this)
        val mainActivity = MainActivity()

        simNumberInput.text = SpannableStringBuilder(deviceInfo.getSimNumber())
        val simNumber = simNumberInput.text
        resetUsage.setOnClickListener{
            deviceInfo.setSimNumber(simNumber)
            activeUsageSession.storeCurrentExistingUsageData()
            startActivity(Intent(this, mainActivity.javaClass))
        }

    }


}