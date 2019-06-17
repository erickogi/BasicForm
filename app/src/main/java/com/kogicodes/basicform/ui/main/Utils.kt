package com.kogicodes.basicform.ui.main

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.agriclinic.common.listeners.OnViewItemClick
import com.agriclinic.common.utils.SimpleDialogModel
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Utils {
    fun loadImage(context: Context, uri: Uri?, avatar: ImageView) {
        Glide.with(context).load(uri).into(avatar)
    }

    companion object {
        private val NAME_VALIDATION_MSG = "Enter a valid name"

        private var cdialog: AlertDialog? = null
        fun simpleYesNoDialog(
            context: Context,
            title: String,
            message: String,
            simpleDialogModel: SimpleDialogModel,
            onclick: OnViewItemClick
        ) {
            if (cdialog != null) {
                cdialog!!.dismiss()
            }
            val builder = AlertDialog.Builder(context)

            builder.setTitle(title)

            builder.setMessage(message)

            if (simpleDialogModel.positive != null) {

                builder.setPositiveButton(simpleDialogModel.positive) { dialog, which ->
                    cdialog?.dismiss()
                    try {
                        onclick.onPositiveClick()
                        cdialog = null
                    } catch (e: Exception) {
                    }
                }
            }

            if (simpleDialogModel.negative != null) {

                builder.setNegativeButton(simpleDialogModel.negative) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNegativeClick()
                    cdialog = null
                }
            }

            if (simpleDialogModel.neutral != null) {
                builder.setNeutralButton(simpleDialogModel.neutral) { dialog, which ->
                    cdialog?.dismiss()
                    onclick.onNeutral()
                    cdialog = null
                }
            }


            val dialog: AlertDialog = builder.create()
            cdialog = dialog


            dialog.show()
        }

        private fun setError(data: Any, error: String?) {
            if (data is TextInputEditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).error = error
                } else {
                    data.error = error
                }
            }
        }

        fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString()
            } else if (data is String) {
                str = data
            }
            return str
        }

        fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }
    }
}