package com.example.touristo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.formData.CardValidation
import com.example.touristo.modal.Booking
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.repository.BookingRepository
import com.example.touristo.repository.PaymentRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
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
    private var count = 0;
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        println(dateFormat)
        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        val user = bundle?.getSerializable("user") as? User

        if(villa!=null){
            tvPaymentToPay = findViewById(R.id.tvPaymentToPay)
            etPaymentCArdNumber = findViewById(R.id.etPaymentCArdNumber)
            etPaymentMonth = findViewById(R.id.etPaymentMonth)
            etPaymentYear = findViewById(R.id.etPaymentYear)
            etPaymentCVC = findViewById(R.id.etPaymentCVC)
            etCardHoldrName = findViewById(R.id.etCardHoldrName)
            btnPaymentPayNow = findViewById(R.id.btnPaymentPayNow)
            btnPaymentDecline = findViewById(R.id.btnPaymentDecline)

            tvPaymentToPay.text =  "Rs ${villa.price.toInt().toString()}"

            btnPaymentPayNow.setOnClickListener {
                showCustomDialogWithAutoLayoutHeight(this@Payment)
            }
        }


    }
    private suspend fun userPaymentSubmission(villa: Villa,user: User){

        val dbcname = etCardHoldrName.text.toString()
        val dbcmonth = etPaymentMonth.text.toString()
        val dbcyear = etPaymentYear.text.toString()
        val dbcnum = etPaymentCArdNumber.text.toString()
        val dbcvc = etPaymentCVC.text.toString()

        val cardValidation = CardValidation(
            dbcname,
            dbcvc.toInt(),
            dbcnum,
            dbcmonth.toInt(),
            dbcyear.toInt()
        )
        val cnameValidation = cardValidation.validateCardName()
        val cnumValidation = cardValidation.validateCardNumber()
        val cvcValidation = cardValidation.validateCVC()
        val cmonthValidation = cardValidation.validateMonth()
        val cyearValidation = cardValidation.validateYear()

        when(cnameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etCardHoldrName.error =cnameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etCardHoldrName.error =cnameValidation.errorMessage
                }

            }
        }

        when(cnumValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentCArdNumber.error =cnumValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentCArdNumber.error =cnumValidation.errorMessage
                }

            }
        }

        when(cvcValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentCVC.error =cvcValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentCVC.error =cvcValidation.errorMessage
                }

            }
        }

        when(cmonthValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentMonth.error =cmonthValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentMonth.error =cmonthValidation.errorMessage
                }

            }
        }

        when(cyearValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentYear.error =cyearValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etPaymentYear.error =cyearValidation.errorMessage
                }

            }
        }

        if(count==5){
            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)
            //get the DAo instances
            val paymentDao = db.paymentDao()
            val bookingDao = db.bookingDao()
            //get the repositories
            val paymentRepo = PaymentRepository(paymentDao, Dispatchers.IO)
            val bookingRepo = BookingRepository(bookingDao, Dispatchers.IO)

            val payObj = com.example.touristo.modal.Payment(0, dbcname,dbcnum,dbcmonth.toInt(),dbcyear.toInt(),dbcvc.toInt(),currentDateTime.toString(),"")
            paymentRepo.insertPayment(payObj)

            //get tHe lastInserteed PaymentId
            val lastPayId = paymentRepo.getPaymentId(dbcname,dbcnum,dbcmonth.toInt(),dbcyear.toInt())

            val bookingObj = Booking(0,user.uemail,villa.id,villa.villaName,lastPayId,currentDateTime.toString(),"")
            bookingRepo.insertBooking(bookingObj)

            GlobalScope.launch(Dispatchers.Main) {
                showCustomDialogWithAutoLayoutHeightForThePaymentConfirmation(this@Payment)
            }


            count=0
        }
        count=0
    }
    private fun showCustomDialogWithAutoLayoutHeight(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_info)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val btnYes = dialog.findViewById<Button>(R.id.btnYes)
        val btnNo = dialog.findViewById<Button>(R.id.btnNo)

        btnYes.setOnClickListener {
            val bundle = intent.extras
            val villa = bundle?.getSerializable("villa") as? Villa
            val user = bundle?.getSerializable("user") as? User
            if (villa != null) {
                GlobalScope.launch(Dispatchers.IO){
                    if (user != null) {
                        userPaymentSubmission(villa,user)
                    }
                }
            }
            dialog.dismiss()

        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showCustomDialogWithAutoLayoutHeightForThePaymentConfirmation(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnDgOk = dialog.findViewById<Button>(R.id.btnDgOk)

        btnDgOk.setOnClickListener {
            val intent = Intent(this@Payment,Thanku::class.java)
            startActivity(intent)
            finish()
        }

        dialog.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.Main) {
            dialog = Dialog(this@Payment)
            dialog.dismiss() // Dismiss the dialog if it's still showing
        }

    }
}