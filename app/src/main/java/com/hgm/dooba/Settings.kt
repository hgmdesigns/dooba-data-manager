package com.hgm.dooba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import com.hgm.dooba.dataUsageStorage.ActiveUsageSession
import com.hgm.dooba.deviceInfo.GetDeviceInfo
import kotlinx.android.synthetic.main.activity_settings.*


class Settings : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val usageLimitStore = ActiveUsageSession(this)
        val activeUsageSession = ActiveUsageSession(this)
        val deviceInfo = GetDeviceInfo(this)
        val mainActivity = MainActivity()

        simNumberInput.text = SpannableStringBuilder(deviceInfo.getSimNumber())
        usageLimitInput.text = SpannableStringBuilder(usageLimitStore.getUsageLimit().toString())

        val usageLimit = usageLimitInput.text
        val simNumber = simNumberInput.text

        resetUsage.setOnClickListener{
            if (password.text?.toString().equals("hgm")!!) {
                deviceInfo.setSimNumber(simNumber)
                activeUsageSession.storeCurrentExistingUsageData()
                usageLimitStore.storeUsageLimit(usageLimit)
                startActivity(Intent(this, mainActivity.javaClass))
                Toast.makeText(this,"Sim Number Changed & Usage Reset!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Password Incorrect", Toast.LENGTH_SHORT).show()
            }

        }

    }


}