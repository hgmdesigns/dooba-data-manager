package com.hgm.dooba.passwordManager

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hgm.dooba.passwordManager.PasswordListener.EditNameDialogListener

class DialogDemoActivity : AppCompatActivity(), EditNameDialogListener {
    // ...
    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    override fun onFinishEditDialog(inputText: String?) {
        Toast.makeText(this, "Hi, $inputText", Toast.LENGTH_SHORT).show()
    }
}