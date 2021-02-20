package com.hgm.dooba.passwordManager

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.DialogFragment

class PasswordListener : DialogFragment(),
    OnEditorActionListener {
    private val mEditText: EditText? = null

    // 1. Defines the listener interface with a method passing back data result.
    interface EditNameDialogListener {
        fun onFinishEditDialog(inputText: String?)
    }

    // ...
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        // ...
        // 2. Setup a callback when the "Done" button is pressed on keyboard
        mEditText!!.setOnEditorActionListener(this)
    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    override fun onEditorAction(
        v: TextView,
        actionId: Int,
        event: KeyEvent
    ): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            val listener = activity as EditNameDialogListener?
            listener!!.onFinishEditDialog(mEditText!!.text.toString())
            // Close the dialog and return back to the parent activity
            dismiss()
            return true
        }
        return false
    }
}