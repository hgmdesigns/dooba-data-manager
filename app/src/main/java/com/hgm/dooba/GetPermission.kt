package com.hgm.dooba

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

class GetPermission(private val context: Context) {
    // Check if permission is granted
     fun hasPhoneStatePermission() =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

     fun hasPackageUsagePermission() =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED
    // Create an array of permissions to request
     fun requestPermission() {
        var permissionToRequest = mutableListOf<String>()
        if(!hasPhoneStatePermission()) {
            permissionToRequest.add(Manifest.permission.READ_PHONE_STATE)
        }
        if(!hasPhoneStatePermission()) {
            permissionToRequest.add(Manifest.permission.PACKAGE_USAGE_STATS)
        }

        if(permissionToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(context as Activity, permissionToRequest.toTypedArray(), 0)
        }
    }


}