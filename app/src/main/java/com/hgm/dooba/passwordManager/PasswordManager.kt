package com.hgm.dooba.passwordManager

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hgm.dooba.R
import com.hgm.dooba.Settings
import kotlinx.android.synthetic.main.dialog_signin.*

class PasswordManager(): DialogFragment(),
    TextView.OnEditorActionListener {
    interface EditNameDialogListener {
        fun onFinishEditDialog(inputText: String?)
    }

    private val settingsActivity = Settings()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                // Add action buttons
                .setPositiveButton(
                    R.string.signin,
                    DialogInterface.OnClickListener { dialog, id ->
                        val listener = activity as PasswordManager.EditNameDialogListener?
                        listener!!.onFinishEditDialog(password!!.text.toString())
                        // Close the dialog and return back to the parent activity
                        dismiss()
//                        startActivity(Intent(context, settingsActivity.javaClass))

                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): PasswordManager {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = PasswordManager()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onEditorAction(
        v: TextView,
        actionId: Int,
        event: KeyEvent
    ): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            val listener = activity as PasswordManager.EditNameDialogListener?
            listener!!.onFinishEditDialog(password!!.text.toString())
            // Close the dialog and return back to the parent activity
            dismiss()
            return true
        }
        return false
    }
}
