package com.example.touristo.dialogAlerts

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.touristo.R

class YesNoDialog(
    private val context: Context
) {
    private var dialog = Dialog(context)

    fun yesNoConfirmDialog(onCancel: () -> Unit,onConfirm: () -> Unit){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_info)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnYes = dialog.findViewById<Button>(R.id.btnYes)
        val btnNo = dialog.findViewById<Button>(R.id.btnNo)

        btnYes.setOnClickListener {
            onConfirm()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            onCancel()
            dialog.dismiss()
        }
        dialog.show()
    }
}