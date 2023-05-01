package com.example.touristo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.modalDTO.FavouriteDTO
import com.example.touristo.repository.BookingRepository
import com.example.touristo.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserHomeFVAdapter(
    var context: Context,
):RecyclerView.Adapter<UserHomeFVViewHolder>() {
    private lateinit var db : TouristoDB
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
        holder.bind(favList[position])
        holder.itemView.findViewById<CardView>(R.id.btnDltFavCardView).setOnClickListener {
            showConfirmationDialog(position,favList[position])
        }
    }
    fun setList(favouriteDTO: List<FavouriteDTO>){
        favList.clear()
        favList.addAll(favouriteDTO)
    }
    fun deleteList(position: Int){
        favList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    private fun showConfirmationDialog(position: Int,favouriteDTO: FavouriteDTO) {
        val yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val favDAo = db.favouriteDao()
                val favouriteRepo = FavouriteRepository(favDAo, Dispatchers.IO)
                var result = favouriteRepo.deleteItemFromBookingList(favouriteDTO.id)
            }
            deleteList(position)
        })

    }
}
class UserHomeFVViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(favouriteDTO: FavouriteDTO){
        val btnDltFavCardView = view.findViewById<CardView>(R.id.btnDltFavCardView)
        val tvFavListDate = view.findViewById<TextView>(R.id.tvFavListDate)
        val tvFAVlISTPrice = view.findViewById<TextView>(R.id.tvFAVlISTPrice)
        val tvFavListName = view.findViewById<TextView>(R.id.tvFavListName)
        val smimgFacIMG = view.findViewById<ImageView>(R.id.smimgFacIMG)

        tvFAVlISTPrice.text = "Rs " +String.format("%.2f",favouriteDTO.price)
        tvFavListDate.text = favouriteDTO.added
        tvFavListName.text = favouriteDTO.villaName


//        btnDltFavCardView.setOnClickListener {
//            onClickDltBtn(favouriteDTO)
//        }
    }
}