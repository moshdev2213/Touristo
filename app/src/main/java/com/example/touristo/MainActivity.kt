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
import androidx.lifecycle.ViewModelProvider
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.repository.PaymentRepository
import com.example.touristo.repository.VillaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var btnMainGo:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        window.decorView.apply {
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        }
        btnMainGo=findViewById(R.id.btnMainLaunch)

        btnMainGo.setOnClickListener {
            val villa = listOf(
               Villa(1,"KabanaFreek",15000.00,"ultra deluxe",4.5,"You will see this error when you upload anything other than JPG or PNG files. However, sometimes it can also happen with a file that appears to be a JPG, according to the image's properties in Windows or Mac. In this case check the image's properties e.g. with the free GIMP or Photoshop. In many cases the file that appears to be a JPG is actually a WEBP file with a JPG file extension.","kurunegala","North Western","propic01","propic01","propic01","propic01","2023-04-19 14:15:29.411",""),
               Villa(2,"ValleyFreek",25000.00,"standard deluxe",2.3,"You will see this error when you upload anything other than JPG or PNG files. However, sometimes it can also happen with a file that appears to be a JPG, according to the image's properties in Windows or Mac. In this case check the image's properties e.g. with the free GIMP or Photoshop. In many cases the file that appears to be a JPG is actually a WEBP file with a JPG file extension.","colombo","Western","propic01","propic01","propic01","propic01","2023-04-19 14:15:29.411",""),
               Villa(3,"CostalFreek",45000.00,"normal deluxe",4.8,"You will see this error when you upload anything other than JPG or PNG files. However, sometimes it can also happen with a file that appears to be a JPG, according to the image's properties in Windows or Mac. In this case check the image's properties e.g. with the free GIMP or Photoshop. In many cases the file that appears to be a JPG is actually a WEBP file with a JPG file extension.","puttlam","North Western","propic01","propic01","propic01","propic01","2023-04-19 14:15:29.411",""),
               Villa(4,"CostalKabana",55000.00,"ultra deluxe",3.7,"You will see this error when you upload anything other than JPG or PNG files. However, sometimes it can also happen with a file that appears to be a JPG, according to the image's properties in Windows or Mac. In this case check the image's properties e.g. with the free GIMP or Photoshop. In many cases the file that appears to be a JPG is actually a WEBP file with a JPG file extension.","hambantota","Southern","propic01","propic01","propic01","propic01","2023-04-19 14:15:29.411",""),
            )
            GlobalScope.launch(Dispatchers.IO){
                // Get an instance of the TouristoDB database
                val db = TouristoDB.getInstance(application)
                //get the DAo instances
                val villaDao = db.villaDao()
                //get the repositories
                val villaRepo = VillaRepository(villaDao, Dispatchers.IO)
                villaRepo.insertListOfVilla(villa)
            }
           val intent = Intent(this@MainActivity, UserLogin::class.java)
           startActivity(intent)
           finish()
        }


    }
    fun showCustomDialogWithAutoLayoutHeight(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_info)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }
}