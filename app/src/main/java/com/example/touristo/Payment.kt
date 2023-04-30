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
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.formData.CardValidation
import com.example.touristo.modal.Booking
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.repository.BookingRepository
import com.example.touristo.repository.PaymentRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


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
    private lateinit var yesNoDialog: YesNoDialog
    private lateinit var progressLoader: ProgressLoader
    private lateinit var confirmationDialog: ConfirmationDialog
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
                yesNoDialog = YesNoDialog(this@Payment)
                yesNoDialog.yesNoConfirmDialog({
                    //do anthing
                },{
                    val bundle = intent.extras
                    val villa = bundle?.getSerializable("villa") as? Villa
                    val user = bundle?.getSerializable("user") as? User
                    if (villa != null) {
                        lifecycleScope.launch(Dispatchers.IO){
                            if (user != null) {
                                userPaymentSubmission(villa,user)
                            }
                        }
                    }
                })
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
        println(count)

        if(count==5){
            val reference = "RF"+ Random.nextInt(1000000, 9999999)

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

            lifecycleScope.launch (Dispatchers.Main){
                progressLoader = ProgressLoader(
                    this@Payment,
                    "Verifying",
                    "Processing Please Wait..."
                )
                progressLoader.startProgressLoader()
                val payObj = com.example.touristo.modal.Payment(0, user.uemail,dbcname,dbcnum,dbcmonth.toInt(),dbcyear.toInt(),dbcvc.toInt(),currentDateTime.toString(),"")
                paymentRepo.insertPayment(payObj)

                //get tHe lastInserteed PaymentId
                val lastPayId = paymentRepo.getPaymentId(dbcname,dbcnum,dbcmonth.toInt(),dbcyear.toInt())

                delay(5000L) // delay for 5 seconds
                val bookingObj = Booking(0,user.uemail,villa.id,villa.villaName,reference,lastPayId,currentDateTime.toString(),"")
                bookingRepo.insertBooking(bookingObj)

                progressLoader.dismissProgressLoader() // dismiss the dialog

                confirmationDialog = ConfirmationDialog(this@Payment)
                confirmationDialog.dialogWithSuccess(
                    "Payment Successful"
                ) {

                    val bundle = Bundle().apply {
                        putSerializable("villa", villa)
                        putSerializable("user", user)
                    }
                    val intent = Intent(this@Payment, Thanku::class.java)
                    intent.putExtras( bundle)
                    startActivity(intent)
                    finish()

                }
            }

            count=0
        }
        count=0
        println(count)
    }

}