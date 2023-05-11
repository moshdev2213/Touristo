package com.example.touristo.dialogAlerts

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.touristo.R

class AdminInqueryModal(
    private val context: Context
) {
    private var dialog = Dialog(context)
    private lateinit var  dgDescription : TextView
    private lateinit var dgCloseBtn : Button
    private lateinit var email : TextView
    private lateinit var date : TextView

    fun showModal(email:String , description:String , date:String,onDismiss: () -> Unit){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_admin_notification_inquiry)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dgDescription = dialog.findViewById(R.id.tvInqueryDescription)
        this.date = dialog.findViewById(R.id.btnAddedInquery)
        dgCloseBtn = dialog.findViewById(R.id.btnCloseInquery)
        this.email = dialog.findViewById(R.id.tvInqueryAdminOfY)

        dgDescription.text = description
        this.date.text = date
        this.email.text = email

        dgCloseBtn.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        dialog.show()
    }
}