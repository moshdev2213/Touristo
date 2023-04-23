package com.example.touristo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa

class UserHomeBLAdapter(
    private val clickListner: (Villa) -> Unit
):RecyclerView.Adapter<UserHomeBlViewHolder>() {

    private val bookingList = ArrayList<Villa>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeBlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.booking_list,parent,false)
        return UserHomeBlViewHolder(listItem)
    }

    override fun getItemCount(): Int {
       return  bookingList.size
    }

    override fun onBindViewHolder(holder: UserHomeBlViewHolder, position: Int) {
       holder.bind(bookingList[position],clickListner)
    }
    fun setList(villa: List<Villa>){
        bookingList.clear()
        bookingList.addAll(villa)
    }

}
class UserHomeBlViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(villa: Villa, clickListner:(Villa)->Unit){
        val ImgProductCard = view.findViewById<ImageView>(R.id.smigBookingImage)
        val tvBookingListName = view.findViewById<ImageView>(R.id.tvBookingListName)
        val tvBookingListDistrict = view.findViewById<ImageView>(R.id.tvBookingListDistrict)
        val tvBookingListPrice = view.findViewById<ImageView>(R.id.tvBookingListPrice)
        val tvBookingListDate = view.findViewById<ImageView>(R.id.tvBookingListDate)

        view.setOnClickListener {
            clickListner(villa)
        }



    }
}