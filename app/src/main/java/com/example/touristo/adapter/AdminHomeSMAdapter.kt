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
import com.example.touristo.modal.User
import com.example.touristo.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminHomeSMAdapter(
    var context: Context,
    private val staffProfileView:(Admin)->Unit,
    private val staffCardClicked:(Admin)->Unit

):RecyclerView.Adapter<AdminHomeSMViewHolder>() {

    private lateinit var db : TouristoDB
    private val adminList = ArrayList<Admin>()
    private lateinit var yesNoDialog: YesNoDialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeSMViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.admin_staff_list,parent,false)
        return AdminHomeSMViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return adminList.size
    }

    override fun onBindViewHolder(holder: AdminHomeSMViewHolder, position: Int) {
        holder.bind(adminList[position],staffProfileView,staffCardClicked)
        holder.itemView.findViewById<CardView>(R.id.staffMcardViewDlt).setOnClickListener {
            showConfirmationDialog(position,adminList[position])
        }
        holder.itemView.findViewById<CardView>(R.id.staffMEditProfileCard).setOnClickListener {
            staffProfileView(adminList[position])
            updateItem()
        }

    }

    fun setList(admin: List<Admin>){
        adminList.clear()
        adminList.addAll(admin)
        notifyDataSetChanged()
    }
    fun deleteItem(position: Int) {
        adminList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }
    fun updateItem(){
        notifyDataSetChanged()
    }

    private fun showConfirmationDialog(position: Int,admin: Admin) {
        val yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val adminDao = db.adminDao()
                val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
                adminRepo.deleteAdmin(admin)
            }
            deleteItem(position)
        })

    }
}
class AdminHomeSMViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(admin: Admin,staffProfileView:(Admin)->Unit,staffCardClicked:(Admin)->Unit){
        val staffMEmail = view.findViewById<TextView>(R.id.staffMEmail)
        val staffMTel = view.findViewById<TextView>(R.id.staffMTel)
        val staffMName = view.findViewById<TextView>(R.id.staffMName)

        val staffMcardViewDlt = view.findViewById<CardView>(R.id.staffMcardViewDlt)
        val staffMEditProfileCard = view.findViewById<CardView>(R.id.staffMEditProfileCard)
        val StaffMCardClick = view.findViewById<CardView>(R.id.StaffMCardClick)

        staffMEmail.text = admin.aemail
        staffMTel.text = admin.tel
        staffMName.text = admin.fname


        StaffMCardClick.setOnClickListener {
            staffCardClicked(admin)
        }
    }
}