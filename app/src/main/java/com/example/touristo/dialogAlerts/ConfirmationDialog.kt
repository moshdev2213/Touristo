package com.example.touristo.dialogAlerts

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.touristo.R

class ConfirmationDialog(
    private val context: Context,
) {

    private var dialog = Dialog(context)
    private lateinit var  dgDescription : TextView
    private lateinit var dgOkBtn : TextView
    private lateinit var imgDg : ImageView

    fun dialogWithSuccess(description: String,onDismiss: () -> Unit) {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dgDescription = dialog.findViewById(R.id.tvDgDecsrition)
        dgOkBtn = dialog.findViewById(R.id.btnDgOk)
        imgDg = dialog.findViewById(R.id.imgDg)

        // Get resource ID of image based on title
        val resourceId = context.resources.getIdentifier("success", "drawable", context.packageName)
        // Set image using resource ID
        imgDg.setImageResource(resourceId)
        dgDescription.text = description

        val color:Int = ContextCompat.getColor(context, R.color.bgDialogSuccess)
        dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

        dgOkBtn.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        dialog.show()
    }
    fun dialogWithError(description: String,onDismiss: () -> Unit){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dgDescription = dialog.findViewById(R.id.tvDgDecsrition)
        dgOkBtn = dialog.findViewById(R.id.btnDgOk)
        imgDg = dialog.findViewById(R.id.imgDg)

        // Get resource ID of image based on title
        val resourceId = context.resources.getIdentifier("error", "drawable", context.packageName)
        // Set image using resource ID
        imgDg.setImageResource(resourceId)
        dgDescription.text = description
        val color:Int = ContextCompat.getColor(context, R.color.bgDialogError)
        dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

        dgOkBtn.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        dialog.show()
    }
    fun dialogWithInfo(description: String,onDismiss: () -> Unit){

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dgDescription = dialog.findViewById(R.id.tvDgDecsrition)
        dgOkBtn = dialog.findViewById(R.id.btnDgOk)
        imgDg = dialog.findViewById(R.id.imgDg)

        // Get resource ID of image based on title
        val resourceId = context.resources.getIdentifier("infocoloredred", "drawable", context.packageName)
        // Set image using resource ID
        imgDg.setImageResource(resourceId)
        dgDescription.text = description
        val color:Int = ContextCompat.getColor(context, R.color.bgDialogError)
        dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

        dgOkBtn.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        dialog.show()
    }
}