package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.touristo.Fragments.AdminHomeFrag
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.modal.LogTime
import com.example.touristo.repository.AdminRepository
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Timestamp

class ScanQR : AppCompatActivity() {

    private lateinit var btnScanBack:Button
    private lateinit var btnScan:Button
    private lateinit var progressLoader: ProgressLoader
    private lateinit var confirmationDialog: ConfirmationDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)



        btnScanBack = findViewById(R.id.btnScanBack)
        btnScan = findViewById(R.id.btnScan)

        btnScanBack.setOnClickListener {
            startActivity(Intent(this@ScanQR,AdminLogin::class.java))
            finish()
        }
        btnScan.setOnClickListener {
            val intergrater = IntentIntegrator(this@ScanQR)
            intergrater.setOrientationLocked(false)
            intergrater.setPrompt("Scan QR Code")
            intergrater.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intergrater.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(intentResult !=null && intentResult.contents!=null){
            val emailByCard = intentResult.contents.toString()
            loginByCard(emailByCard)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun loginByCard(email:String){
        val currentDateTime = Timestamp(System.currentTimeMillis())
        lifecycleScope.launch(Dispatchers.IO){
            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val adminDao = db.adminDao()
            val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
            val existChecker :Int = adminRepo.loginByCard(email)

            lifecycleScope.launch(Dispatchers.Main){

                if(existChecker==1){
                    progressLoader = ProgressLoader(
                        this@ScanQR,"Logging In","Please Wait..."
                    )
                    progressLoader.startProgressLoader()

                    adminRepo.insertLoggedTime(LogTime(0,email,"admin",currentDateTime.toString()))

                    val intent = Intent(this@ScanQR,AdminHome::class.java)
                    intent.putExtra("adminEmail", email)
                    val fragment = AdminHomeFrag()
                    fragment.arguments = intent.extras

                    delay(3000L) // delay for 5 seconds
                    progressLoader.dismissProgressLoader() // dismiss the dialog
                    startActivity(intent)
                    finish()
                }else{
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@ScanQR)
                    //lamdas starts here
                    confirmationDialog.dialogWithError("Invalid Card Detected") {

                    }
                }
            }

        }
    }
}