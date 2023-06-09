package com.example.touristo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.Favourite
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.repository.FavouriteRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*

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
    private lateinit var db : TouristoDB
    private lateinit var dialog: Dialog
    @SuppressLint("SetTextI18n")
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

        //initializing the views
        tvProductVillaName = findViewById(R.id.tvProductVillaName)
        imgProductMainImg = findViewById(R.id.imgProductMainImg)
        tvProductVillaPrice = findViewById(R.id.tvProductVillaPrice)
        tvProductVillaReviews = findViewById(R.id.tvProductVillaReviews)
        tvProductVillaRating = findViewById(R.id.tvProductVillaRating)
        rbProductVillaRating = findViewById(R.id.rbProductVillaRating)
        tvProductVillaType = findViewById(R.id.tvProductVillaType)
        tvProductVillaDecription = findViewById(R.id.tvProductVillaDecription)
        simgProductVilla01 = findViewById(R.id.simgProductVilla01)
        simgProductVilla02 = findViewById(R.id.simgProductVilla02)
        simgProductVilla03 = findViewById(R.id.simgProductVilla03)
        simgPorductVilla04 = findViewById(R.id.simgPorductVilla04)
        tvProductVillaPriceSub01 = findViewById(R.id.tvProductVillaPriceSub01)
        tvProductVillaTypeSub02 = findViewById(R.id.tvProductVillaTypeSub02)
        btnProductVillaBookPlace = findViewById(R.id.btnProductVillaBookPlace)

        fbProductBackBtn = findViewById(R.id.fbProductBackBtn)
        fbProductFavouriteBtn = findViewById(R.id.fbProductFavouriteBtn)

        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        val user = bundle?.getSerializable("user") as? User

        if (villa!=null){
            // Get the resource ID of the drawable using the drawable name
            val img01 = villa.img01
            val img02 = villa.img02
            val img03 = villa.img03
            val img04 = villa.img04
            val drawableResId01 = resources.getIdentifier(img01, "drawable", packageName)
            val drawableResId02 = resources.getIdentifier(img02, "drawable", packageName)
            val drawableResId03 = resources.getIdentifier(img03, "drawable", packageName)
            val drawableResId04 = resources.getIdentifier(img04, "drawable", packageName)
            // Set the drawable to the ImageView using the resource ID
            imgProductMainImg.setImageResource(drawableResId01)
            simgProductVilla01.setImageResource(drawableResId01)
            simgProductVilla02.setImageResource(drawableResId02)
            simgProductVilla03.setImageResource(drawableResId03)
            simgPorductVilla04.setImageResource(drawableResId04)

            tvProductVillaName.text = villa.villaName.toUpperCase()
            tvProductVillaPrice.text = "Rs ${villa.price.toInt().toString()}"
            tvProductVillaType.text = "Room ${villa.roomType}"
            tvProductVillaDecription.text = villa.description
            tvProductVillaReviews.text = villa.added.toString()
            tvProductVillaRating.text = "${villa.rating} Reviews"
            tvProductVillaPriceSub01.text = "Rs " + String.format("%.2f", villa.price)
            tvProductVillaTypeSub02.text = "Room ${villa.roomType}"

            rbProductVillaRating.rating = villa.rating.toFloat()

            fbProductBackBtn.setOnClickListener{
                finish()
            }
            fbProductFavouriteBtn.setOnClickListener{
                lifecycleScope.launch(Dispatchers.IO) {
                    if (user != null) {
                        insertIntoFavourites(villa,user.uemail)
                    }
                }
            }
            btnProductVillaBookPlace.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("villa", villa)
                    putSerializable("user", user)
                }
                val intent = Intent(this@Product,Checkout::class.java)
                intent.putExtras( bundle)
                startActivity(intent)
                finish()
            }

        }
    }

    suspend private fun insertIntoFavourites(villa: Villa,email:String) {
        //initializing db Credentils and storing data
        val currentDateTime = Timestamp(System.currentTimeMillis())
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(this@Product)
        val favouriteDao = db.favouriteDao()
        val favouriteRepo = FavouriteRepository(favouriteDao,Dispatchers.IO)

        val result = favouriteRepo.getUserAddedOrNot(email,villa.id)
        if(result==0){
            favouriteRepo.insertFavourites(Favourite(0,email,villa.id,currentDateTime.toString(),""))
            GlobalScope.launch(Dispatchers.Main){
                showCustomDialogWithAutoLayoutHeight(this@Product,"success","Added To Favourites")
            }
        }else{
            GlobalScope.launch(Dispatchers.Main){
                showCustomDialogWithAutoLayoutHeight(this@Product,"infocoloredred","Already Exists")
            }
        }
    }
    private fun showCustomDialogWithAutoLayoutHeight(context: Context, title :String, description:String){
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dgDescription = dialog.findViewById<TextView>(R.id.tvDgDecsrition)
        val dgOkBtn = dialog.findViewById<TextView>(R.id.btnDgOk)
        val imgDg = dialog.findViewById<ImageView>(R.id.imgDg)

        // Get resource ID of image based on title
        val resourceId = context.resources.getIdentifier(title, "drawable", context.packageName)
        // Set image using resource ID
        imgDg.setImageResource(resourceId)
        dgDescription.text = description

        val color:Int
        if(title.equals("infocoloredred", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogError)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                dialog.dismiss()
            }
        }else if(title.equals("success", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogSuccess)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.Main) {
            dialog = Dialog(this@Product)
            dialog.dismiss() // Dismiss the dialog if it's still showing
        }

    }
}