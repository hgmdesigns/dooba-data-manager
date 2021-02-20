package com.hgm.dooba.dataUsageStorage

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class UsageStorage(private val context: Context) {

    fun writeFileOnInternalStorage(fileKey: String, sBody: String) {
        val file = File(context?.filesDir, "files")
        try {
            if (!file.exists()) {
                file.mkdir()
            }
            val fileToWrite = File(file, fileKey)
            val writer = FileWriter(fileToWrite)
            writer.append(sBody)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            Log.e("ERROR".toString(), "${e}")
        }
    }

    fun readFileOnInternalStorage(fileKey: String): String {
        val file = File(context?.filesDir, "files")
        var ret = ""
        try {
            if (!file.exists()) {
                return ret
            }
            val fileToRead = File(file, fileKey)
            val reader = FileReader(fileToRead)
            ret = reader.readText()
            reader.close()
        } catch (e: Exception) {
            Log.e("ERROR".toString(), "${e}")
        }
        return ret
    }
}