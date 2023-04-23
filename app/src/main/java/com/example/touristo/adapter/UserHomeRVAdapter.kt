package com.example.touristo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.modal.Villa

class UserHomeRVAdapter():RecyclerView.Adapter<UserHomeViewHolder>() {

    private val villaList = ArrayList<Villa>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.home_list,parent,false)
        return UserHomeViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return villaList.size
//        return 5
    }

    override fun onBindViewHolder(holder: UserHomeViewHolder, position: Int) {
        holder.bind(villaList[position])
    }

    fun setList(villa: List<Villa>){
        villaList.clear()
        villaList.addAll(villa)
    }
}

class UserHomeViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(villa:Villa){
        val ImgProductCard = view.findViewById<ImageView>(R.id.ImgProductCard)
        val tvCityName = view.findViewById<TextView>(R.id.tvCityName)
        val tvPlaceDate = view.findViewById<TextView>(R.id.tvPlaceDate)
        val tvProductPrice = view.findViewById<TextView>(R.id.tvProductPrice)
        val imgFavouriteProduct = view.findViewById<ImageView>(R.id.imgFavouriteProduct)
        val rbProductCard = view.findViewById<RatingBar>(R.id.rbProductCard)
    }
}