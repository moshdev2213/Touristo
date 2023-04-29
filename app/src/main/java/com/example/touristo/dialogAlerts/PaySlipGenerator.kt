package com.example.touristo.dialogAlerts

import android.app.Dialog
import android.content.Context
import android.widget.ImageView
import android.widget.TextView

class PaySlipGenerator(
    private val context: Context
) {
    private var dialog = Dialog(context)
    private lateinit var  dgDescription : TextView
    private lateinit var dgOkBtn : TextView
    private lateinit var imgDg : ImageView
}