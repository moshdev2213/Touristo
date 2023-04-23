package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Product : AppCompatActivity() {

    private lateinit var btnProductVillaBookPlace:Button
    private lateinit var tvProductVillaTypeSub02:TextView
    private lateinit var tvProductVillaPriceSub01:TextView
    private lateinit var simgPorductVilla04:ImageView
    private lateinit var simgProductVilla03:ImageView
    private lateinit var simgProductVilla02:ImageView
    private lateinit var simgProductVilla01:ImageView
    private lateinit var tvProductVillaReviews:TextView
    private lateinit var tvProductVillaRating:TextView
    private lateinit var tvProductVillaDecription:TextView
    private lateinit var tvProductVillaType:TextView
    private lateinit var rbProductVillaRating:RatingBar
    private lateinit var tvProductVillaPrice:TextView
    private lateinit var tvProductVillaName:TextView
    private lateinit var imgProductMainImg:ImageView
    private lateinit var tvProductJstHeading:TextView
    private lateinit var fbProductFavouriteBtn:FloatingActionButton
    private lateinit var fbProductBackBtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        if (villa!=null){
            imgProductMainImg
        }
    }
}