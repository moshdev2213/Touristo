package com.example.touristo.dialogAlerts

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.touristo.R
import com.example.touristo.modalDTO.BookingDTO

class PaySlipGenerator(
    private val context: Context
) {
    private var dialog = Dialog(context)

    fun generateSlipDialog(bookingDTO: BookingDTO, onDismiss: () -> Unit, onInquiry: () -> Unit){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.payment_done_receipt)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvPaySlipPrice = dialog.findViewById<TextView>(R.id.tvPaySlipPrice)
        val tvPaySlipReference = dialog.findViewById<TextView>(R.id.tvPaySlipReference)
        val tvPaySlipDate = dialog.findViewById<TextView>(R.id.tvPaySlipDate)
        val tvPaySlipTax = dialog.findViewById<TextView>(R.id.tvPaySlipTax)
        val tvPaySlipStatus = dialog.findViewById<TextView>(R.id.tvPaySlipStatus)
        val tvPaySlipType = dialog.findViewById<TextView>(R.id.tvPaySlipType)
        val tvPaySlipVillaName = dialog.findViewById<TextView>(R.id.tvPaySlipVillaName)

        val btnPaySlipDateOkBtn = dialog.findViewById<Button>(R.id.btnPaySlipDateOkBtn)
        val btnPaySlipDateInquiryBtn2 = dialog.findViewById<Button>(R.id.btnPaySlipDateInquiryBtn2)

        tvPaySlipDate.text = bookingDTO.booked
        tvPaySlipStatus.text = "Paid"
        tvPaySlipReference.text = bookingDTO.reference
        tvPaySlipPrice.text = "Rs "+bookingDTO.price.toString()
        tvPaySlipTax.text = "Rs 00.00"
        tvPaySlipType.text = "Card Payment"
        tvPaySlipVillaName.text = bookingDTO.villaName

        btnPaySlipDateOkBtn.setOnClickListener {
            onDismiss()
            dialog.dismiss()
        }
        btnPaySlipDateInquiryBtn2.setOnClickListener{
            onInquiry()
            dialog.dismiss()
        }
        dialog.show()
    }
}