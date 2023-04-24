package com.example.touristo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO

class UserHomeBLAdapter(
    private val clickListner: (BookingDTO) -> Unit
):RecyclerView.Adapter<UserHomeBlViewHolder>() {

    private val bookingList = ArrayList<BookingDTO>()

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
    fun setList(villa: List<BookingDTO>){
        bookingList.clear()
        bookingList.addAll(bookingList)
    }

}
class UserHomeBlViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(bookingDTO: BookingDTO, clickListner:(BookingDTO)->Unit){
        val ImgProductCard = view.findViewById<ImageView>(R.id.smigBookingImage)
        val tvBookingListName = view.findViewById<ImageView>(R.id.tvBookingListName)
        val btnDltBookingCardView = view.findViewById<CardView>(R.id.btnDltBookingCardView)
        val tvBookingListPrice = view.findViewById<ImageView>(R.id.tvBookingListPrice)
        val tvBookingListDate = view.findViewById<ImageView>(R.id.tvBookingListDate)

        btnDltBookingCardView.setOnClickListener {
            clickListner(bookingDTO)
        }



    }
}