package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.touristo.modal.Villa
import java.text.SimpleDateFormat
import java.util.*

class Payment : AppCompatActivity() {
    private lateinit var btnPaymentDecline:Button
    private lateinit var btnPaymentPayNow:Button
    private lateinit var etCardHoldrName:EditText
    private lateinit var etPaymentCVC:EditText
    private lateinit var etPaymentYear:EditText
    private lateinit var etPaymentMonth:EditText
    private lateinit var etPaymentCArdNumber:EditText
    private lateinit var tvPaymentToPay:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        println(dateFormat)
        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        if(villa!=null){
            tvPaymentToPay = findViewById(R.id.tvPaymentToPay)
            tvPaymentToPay.text =  "Rs ${villa.price.toInt().toString()}"
        }


    }
}