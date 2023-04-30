package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Checkout : AppCompatActivity() {
    private lateinit var btnCheckoutCancel:Button
    private lateinit var btnCheckoutOk:Button
    private lateinit var tvCheckoutTotalPrice:TextView
    private lateinit var tvCheckoutDiscount:TextView
    private lateinit var tvCheckoutSubTotal:TextView
    private lateinit var tvCheckputDate:TextView
    private lateinit var tvCheckoutVillaProvince:TextView
    private lateinit var tvCheckoutVillaPrice:TextView
    private lateinit var tvCheckoutVillaName:TextView
    private lateinit var simgCheckout:ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        val date = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date())

        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        val user = bundle?.getSerializable("user") as? User

        if (villa!=null){
            //initializing the views
            tvCheckoutVillaName = findViewById(R.id.tvCheckoutVillaName)
            tvCheckoutVillaPrice = findViewById(R.id.tvCheckoutVillaPrice)
            tvCheckoutVillaProvince = findViewById(R.id.tvCheckoutVillaProvince)
            tvCheckputDate = findViewById(R.id.tvCheckputDate)
            tvCheckoutSubTotal = findViewById(R.id.tvCheckoutSubTotal)
            tvCheckoutDiscount = findViewById(R.id.tvCheckoutDiscount)
            tvCheckoutTotalPrice = findViewById(R.id.tvCheckoutTotalPrice)
            btnCheckoutOk = findViewById(R.id.btnCheckoutOk)
            btnCheckoutCancel = findViewById(R.id.btnCheckoutCancel)
            simgCheckout = findViewById(R.id.simgCheckout)

            tvCheckoutVillaName.text = villa.villaName.uppercase()
            tvCheckoutVillaProvince.text = villa.province
            tvCheckoutVillaPrice.text = "Rs " + String.format("%.2f", villa.price)
            tvCheckputDate.text = date.toString()
            //for the subtotal also add the discount column in the db later
            tvCheckoutSubTotal.text = "Rs " + String.format("%.2f", villa.price)
            tvCheckoutDiscount.text = "Rs 00.00"
            tvCheckoutTotalPrice.text =  "Rs " + String.format("%.2f", villa.price)

            btnCheckoutCancel.setOnClickListener {
                finish()
            }
            btnCheckoutOk.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("villa", villa)
                    putSerializable("user", user)
                }
                val intent = Intent(this@Checkout,Payment::class.java)
                intent.putExtras( bundle)
                startActivity(intent)
                finish()

            }
        }
    }
}