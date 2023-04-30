package com.example.touristo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.modalDTO.FavouriteDTO

class UserHomeFVAdapter(
    private val onClickDltBtn: (FavouriteDTO) -> Unit
):RecyclerView.Adapter<UserHomeFVViewHolder>() {
    private val favList = ArrayList<FavouriteDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeFVViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.favorite_list,parent,false)
        return UserHomeFVViewHolder(listItem)
    }

    override fun getItemCount(): Int {
       return favList.size
    }

    override fun onBindViewHolder(holder: UserHomeFVViewHolder, position: Int) {
        holder.bind(favList[position], onClickDltBtn)
    }
    fun setList(favouriteDTO: List<FavouriteDTO>){
        favList.clear()
        favList.addAll(favouriteDTO)
    }
}
class UserHomeFVViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(favouriteDTO: FavouriteDTO,onClickDltBtn:(FavouriteDTO)->Unit){
        val btnDltFavCardView = view.findViewById<CardView>(R.id.btnDltFavCardView)
        val tvFavListDate = view.findViewById<TextView>(R.id.tvFavListDate)
        val tvFAVlISTPrice = view.findViewById<TextView>(R.id.tvFAVlISTPrice)
        val tvFavListName = view.findViewById<TextView>(R.id.tvFavListName)
        val smimgFacIMG = view.findViewById<ImageView>(R.id.smimgFacIMG)

        tvFAVlISTPrice.text = "Rs " +String.format("%.2f",favouriteDTO.price)
        tvFavListDate.text = favouriteDTO.added
        tvFavListName.text = favouriteDTO.villaName


        btnDltFavCardView.setOnClickListener {
            onClickDltBtn(favouriteDTO)
        }
    }
}