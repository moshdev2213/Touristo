package com.example.touristo

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.modal.User
import com.example.touristo.modal.UserInquery
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.repository.InquiryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

class UserInquiryForm : AppCompatActivity() {
    private lateinit var etInqUserEmail:EditText
    private lateinit var etInqUserDescription:EditText
    private lateinit var btnInqCancel:Button
    private lateinit var btnInqSubmit:Button
    private lateinit var confirmationDialog: ConfirmationDialog

    private lateinit var db : TouristoDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_inquiry_form)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        val bundle = intent.extras
        val bookingDTO = bundle?.getSerializable("bookingDTO") as? BookingDTO

        btnInqSubmit = findViewById(R.id. btnInqSubmit)
        btnInqCancel = findViewById(R.id. btnInqCancel)
        etInqUserDescription = findViewById(R.id. etInqUserDescription)
        etInqUserEmail = findViewById(R.id. etInqUserEmail)

        etInqUserEmail.setText(bookingDTO?.uemail)
        etInqUserEmail.isEnabled=false

        btnInqCancel.setOnClickListener {
            finish()
        }
        btnInqSubmit.setOnClickListener {
            if(etInqUserDescription.text.toString().isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    if (bookingDTO != null) {
                        insertTheFormSubmit(bookingDTO)
                    }
                }
            }
            else{
                etInqUserDescription.error = "Insert Inquiry"
            }
        }
    }

    private suspend fun insertTheFormSubmit(bookingDTO:BookingDTO){
        btnInqSubmit = findViewById(R.id. btnInqSubmit)
        btnInqCancel = findViewById(R.id. btnInqCancel)
        etInqUserDescription = findViewById(R.id. etInqUserDescription)
        etInqUserEmail = findViewById(R.id. etInqUserEmail)

        if(bookingDTO!=null){

            val decription = etInqUserDescription.text.toString()
            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            db = TouristoDB.getInstance(this@UserInquiryForm)
            val inquiryDao = db.inquiryDao()
            val inquiryRepository = InquiryRepository(inquiryDao, Dispatchers.IO)

            lifecycleScope.launch(Dispatchers.IO){
                val result = inquiryRepository.insertInquiry(UserInquery(
                    0,
                    bookingDTO.uemail,
                    decription,
                    bookingDTO.bookingId,
                    currentDateTime.toString(),
                    ""
                ))
                lifecycleScope.launch(Dispatchers.Main){
                    confirmationDialog = ConfirmationDialog(this@UserInquiryForm)
                    if(result==2){
                        confirmationDialog.dialogWithSuccess("Inquery Submitted") {
                            finish()
                        }
                    }else{
                        confirmationDialog.dialogWithError("Submiision Failed"){
                            finish()
                        }
                    }
                }

            }

        }
    }
}