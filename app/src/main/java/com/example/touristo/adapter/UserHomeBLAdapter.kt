package com.example.touristo.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.modalDTO.PayslipDTO
import com.example.touristo.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserHomeBLAdapter(
    var context: Context,
    private val paySlipView:(BookingDTO)->Unit
):RecyclerView.Adapter<UserHomeBlViewHolder>() {

    private lateinit var db : TouristoDB
    private val bookingList = ArrayList<BookingDTO>()
    private lateinit var yesNoDialog: YesNoDialog


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeBlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.booking_list,parent,false)
        return UserHomeBlViewHolder(listItem)
    }

    override fun getItemCount(): Int {
       return  bookingList.size
    }

    override fun onBindViewHolder(holder: UserHomeBlViewHolder, position: Int) {
       holder.bind(bookingList[position],paySlipView, bookingList)
        holder.itemView.findViewById<CardView>(R.id.btnDltBookingCardView).setOnClickListener {
            showConfirmationDialog(position,bookingList[position])
        }
    }
    fun setList(bookingDTO: List<BookingDTO>){
        bookingList.clear()
        bookingList.addAll(bookingDTO)
    }
    fun deleteItem(position: Int) {
        bookingList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun showConfirmationDialog(position: Int,bookingDTO: BookingDTO) {
        val yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val bookDAo = db.bookingDao()
                val bookingRepo = BookingRepository(bookDAo,Dispatchers.IO)
                var result = bookingRepo.deleteItemFromBookingList(bookingDTO.bookingId)
            }
            deleteItem(position)
        })

    }
}
class UserHomeBlViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(bookingDTO: BookingDTO,paySlipView:(BookingDTO)->Unit,bookingList: ArrayList<BookingDTO>){
        val ImgProductCard = view.findViewById<ImageView>(R.id.smigBookingImage)
        val tvBookingListName = view.findViewById<TextView>(R.id.tvBookingListName)
        val btnDltBookingCardView = view.findViewById<CardView>(R.id.btnDltBookingCardView)
        val tvBookingListPrice = view.findViewById<TextView>(R.id.tvBookingListPrice)
        val tvBookingListDate = view.findViewById<TextView>(R.id.tvBookingListDate)
        val btnWatchBookingCardView = view.findViewById<CardView>(R.id.btnWatchBookingCardView)

        tvBookingListDate.text = bookingDTO.booked
        tvBookingListPrice.text =  "Rs " + String.format("%.2f", bookingDTO.price)
        tvBookingListName.text = bookingDTO.villaName.capitalize()

        btnWatchBookingCardView.setOnClickListener {
            paySlipView(bookingDTO)
        }



    }
}