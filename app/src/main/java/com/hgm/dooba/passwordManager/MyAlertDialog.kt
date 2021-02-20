package com.hgm.dooba.passwordManager

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyAlertDialogFragment() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = "title"
        val alertDialogBuilder =
            AlertDialog.Builder()
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage("Are you sure?")
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // on success
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which -> dialog?.dismiss() }
        return alertDialogBuilder.create()
    }

    companion object {
        const val TAG = "SimpleDialog"

        fun newInstance(title: String?): MyAlertDialogFragment {
            val frag = MyAlertDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }
}