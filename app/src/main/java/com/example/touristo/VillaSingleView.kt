package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.VillaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class VillaSingleView : AppCompatActivity() {
    private lateinit var btnVillaBack2Home: Button
    private lateinit var btnRoomType: Button
    private lateinit var tvVillaDistrict: TextView
    private lateinit var tvVillaLastBookedDate: TextView
    private lateinit var tvVillaTotBooks: TextView
    private lateinit var tvVillaTotRate: TextView
    private lateinit var btnVillaTotalPays: Button
    private lateinit var btnVillaPrice: Button
    private lateinit var tvVillaName: TextView
    private lateinit var simgVillaPhoto: ImageView
    private lateinit var openerImg: ImageView

    private lateinit var villa: Villa
    private lateinit var aemail:String
    private lateinit var db: TouristoDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_villa_single_view)
        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        //coe starts here
        openerImg = findViewById(R.id.openerImg)
        btnVillaBack2Home = findViewById(R.id.btnVillaBack2Home)

        btnRoomType= findViewById(R.id.btnRoomType)
        tvVillaDistrict= findViewById(R.id.tvVillaDistrict)
        tvVillaLastBookedDate= findViewById(R.id.tvVillaLastBookedDate)
        tvVillaTotBooks= findViewById(R.id.tvVillaTotBooks)
        tvVillaTotRate= findViewById(R.id.tvVillaTotRate)
        btnVillaTotalPays= findViewById(R.id.btnVillaTotalPays)
        btnVillaPrice= findViewById(R.id.btnVillaPrice)
        tvVillaName= findViewById(R.id.tvVillaName)
        simgVillaPhoto= findViewById(R.id.simgVillaPhoto)

        val bundle = intent.extras
        villa = bundle?.getSerializable("villa") as Villa
        aemail = bundle.getString("amail").toString()

        openerImg.setOnClickListener {
            finish()
        }
        btnVillaBack2Home.setOnClickListener {
            finish()
        }

        btnRoomType.text = villa.roomType.uppercase()
        tvVillaDistrict.text = villa.district.capitalize()
        tvVillaLastBookedDate.text = dateFormatter(villa.added)
        tvVillaTotRate.text = villa.rating.toString()


        btnVillaPrice.text  = "Rs ${String.format("%.2f", villa.price)}"
        tvVillaName.text  = villa.villaName.capitalize()
//        simgVillaPhoto.text  = admin.aemail
        lifecycleScope.launch(Dispatchers.IO){
            getVillaLogs(villa.id,villa.price)
        }
    }

    private fun getVillaLogs(id: Int,price:Double) {

        db = TouristoDB.getInstance(application)
        // Get the adminDao from the database
        val villaDao = db.villaDao()
        val villaRepo = VillaRepository(villaDao, Dispatchers.IO)
        var bookCount = 0
        lifecycleScope.launch(Dispatchers.IO){
            bookCount = villaRepo.getVillaBookCount(id)
        }


        lifecycleScope.launch(Dispatchers.Main){
            tvVillaTotBooks.text = bookCount.toString()
            btnVillaTotalPays.text= "Rs ${String.format("%.2f", (price*bookCount))}"
        }
    }

    private fun dateFormatter(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

        // Convert input string to date
        val date = inputFormat.parse(date)
        // Extract day of month from parsed date
        val dayOfMonth = date.day
        val outputFormat = SimpleDateFormat("dd MMM yy", Locale.getDefault())

        // Format date to output string
        val outputDateString = outputFormat.format(date)
        return outputDateString.toString()

    }
}