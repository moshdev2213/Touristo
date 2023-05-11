package com.example.touristo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.modal.Admin
import com.example.touristo.modal.UserInquery
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.InquiryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminHomeINAdapter(
    var context: Context,
    private val CardClicked:(UserInquery)->Unit
):RecyclerView.Adapter<AdminHomeINHolder>() {

    private lateinit var db : TouristoDB
    private val inqueryList = ArrayList<UserInquery>()
    private lateinit var yesNoDialog: YesNoDialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeINHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.inquiry_list,parent,false)
        return AdminHomeINHolder(listItem)
    }

    override fun getItemCount(): Int {
        return inqueryList.size
    }

    override fun onBindViewHolder(holder: AdminHomeINHolder, position: Int) {
        holder.bind(inqueryList[position],CardClicked)
        holder.itemView.findViewById<CardView>(R.id.btnDltInqureCardView).setOnClickListener {
            showConfirmationDialog(position,inqueryList[position])
        }

    }
    fun setList(inquery: List<UserInquery>){
        inqueryList.clear()
        inqueryList.addAll(inquery)
        notifyDataSetChanged()
    }
    private fun deleteItem(position: Int) {
        inqueryList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }
    private fun showConfirmationDialog(position: Int,inquery: UserInquery) {
        yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val inquiryDao = db.inquiryDao()
                val inquiryRepo = InquiryRepository(inquiryDao, Dispatchers.IO)
                inquiryRepo.deleteInquiry(inquery)
            }
            deleteItem(position)
        })

    }
}
class AdminHomeINHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(inquery: UserInquery, CardClicked:(UserInquery)->Unit){
        val btnDltInqureCardView = view.findViewById<CardView>(R.id.btnDltInqureCardView)
        val tvFavListDate2 = view.findViewById<TextView>(R.id.tvFavListDate2)
        val tvFAVlISTPrice2 = view.findViewById<TextView>(R.id.tvFAVlISTPrice2)
        val tvFavListName2 = view.findViewById<TextView>(R.id.tvFavListName2)
        val cvAdminNotiyList = view.findViewById<CardView>(R.id.cvAdminNotiyList)

        tvFavListDate2.text = "Booking ID : "+inquery.bookingId.toString()
        tvFAVlISTPrice2.text = inquery.added
        tvFavListName2.text = inquery.uemail


        cvAdminNotiyList.setOnClickListener {
            CardClicked(inquery)
        }
    }
}