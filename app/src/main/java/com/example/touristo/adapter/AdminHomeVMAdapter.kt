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
import com.example.touristo.modal.Villa
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.VillaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminHomeVMAdapter(
    var context: Context,
    private val villaProfileView:(Villa)->Unit,
    private val villaCardClicked:(Villa)->Unit
):RecyclerView.Adapter<AdminHomeVMViewModal>() {

    private lateinit var db : TouristoDB
    private val villaList = ArrayList<Villa>()
    private lateinit var yesNoDialog: YesNoDialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeVMViewModal {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.villa_list_item,parent,false)
        return AdminHomeVMViewModal(listItem)
    }

    override fun getItemCount(): Int {
       return villaList.size
    }

    override fun onBindViewHolder(holder: AdminHomeVMViewModal, position: Int) {
        holder.bind(villaList[position],villaCardClicked)
        holder.itemView.findViewById<CardView>(R.id.cvVillaMDltBTN).setOnClickListener {
            showConfirmationDialog(position,villaList[position])
        }
        holder.itemView.findViewById<CardView>(R.id.cvVillaMEditBTN).setOnClickListener {
            villaProfileView(villaList[position])
            updateItem()
        }
    }

    fun setList(villa: List<Villa>){
        villaList.clear()
        villaList.addAll(villa)
        notifyDataSetChanged()
    }
    fun deleteItem(position: Int) {
        villaList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }
    fun updateItem(){
        notifyDataSetChanged()
    }

    private fun showConfirmationDialog(position: Int,villa: Villa) {
        val yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val villaDao = db.villaDao()
                val villaRepo = VillaRepository(villaDao, Dispatchers.IO)
                villaRepo.deleteVilla(villa)
            }
            deleteItem(position)
        })

    }

}

class AdminHomeVMViewModal(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(villa: Villa,villaCardClicked:(Villa)->Unit,){
        val tvVillaMName = view.findViewById<TextView>(R.id.tvVillaMName)
        val tvVillaLocation = view.findViewById<TextView>(R.id.tvVillaLocation)
        val tvVillaPrice = view.findViewById<TextView>(R.id.tvVillaPrice)

        val cvVillaMDltBTN = view.findViewById<CardView>(R.id.cvVillaMDltBTN)
        val cvVillaMEditBTN = view.findViewById<CardView>(R.id.cvVillaMEditBTN)
        val cvVillaMCard = view.findViewById<CardView>(R.id.cvVillaMCard)

        tvVillaMName.text = villa.villaName.capitalize()
        tvVillaLocation.text = villa.province.capitalize()
        tvVillaPrice.text = "Rs " +String.format("%.2f",villa.price)


        cvVillaMCard.setOnClickListener {
            villaCardClicked(villa)
        }
    }

}