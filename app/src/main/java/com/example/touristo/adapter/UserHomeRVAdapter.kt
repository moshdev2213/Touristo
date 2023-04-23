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

class UserHomeRVAdapter(
    private val clickListner:(Villa)->Unit
):RecyclerView.Adapter<UserHomeViewHolder>() {

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
        holder.bind(villaList[position],clickListner)
    }

    fun setList(villa: List<Villa>){
        villaList.clear()
        villaList.addAll(villa)
    }
}

class UserHomeViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(villa:Villa,clickListner:(Villa)->Unit){
        val ImgProductCard = view.findViewById<ImageView>(R.id.ImgProductCard)
        val tvCityName = view.findViewById<TextView>(R.id.tvCityName)
        val tvPlaceDate = view.findViewById<TextView>(R.id.tvPlaceDate)
        val tvProductPrice = view.findViewById<TextView>(R.id.tvProductPrice)
        val rbProductCard = view.findViewById<RatingBar>(R.id.rbProductCard)

        tvCityName.text = villa.district
        tvPlaceDate.text = villa.added.toString()
        tvProductPrice.text = villa.price.toString()
        rbProductCard.rating = villa.rating.toFloat()

        view.setOnClickListener {
            clickListner(villa)
        }
    }
}