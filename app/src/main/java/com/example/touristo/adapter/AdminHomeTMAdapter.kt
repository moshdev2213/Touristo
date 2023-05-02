package com.example.touristo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.modal.User
import com.example.touristo.modalDTO.BookingDTO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdminHomeTMAdapter(
    var context: Context,
    private val userProfileiew:(User)->Unit,
    private val userCardClicked:(User)->Unit
): RecyclerView.Adapter<AdminHomeTMViewHolder>() {

    private lateinit var db : TouristoDB
    private val userList = ArrayList<User>()
    private lateinit var yesNoDialog: YesNoDialog

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeTMViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.users_list,parent,false)
        return AdminHomeTMViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: AdminHomeTMViewHolder, position: Int) {
        holder.bind(userList[position],userProfileiew,userCardClicked)
        holder.itemView.findViewById<CardView>(R.id.userListDLTbtn).setOnClickListener {
            Toast.makeText(context,"bllooo",Toast.LENGTH_SHORT).show()
        }
    }
    fun setList(user: List<User>){
        userList.clear()
        userList.addAll(user)
        notifyDataSetChanged()
    }
    fun deleteItem(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }
}
class AdminHomeTMViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(user: User,userProfileiew:(User)->Unit,userCardClicked:(User)->Unit){

        val userListUserEmail = view.findViewById<TextView>(R.id.userListUserEmail)
        val userListAddedDate = view.findViewById<TextView>(R.id.userListAddedDate)
        val userListUsrName = view.findViewById<TextView>(R.id.userListUsrName)

        val theUserListCard = view.findViewById<CardView>(R.id.theUserListCard)
        val userListDLTbtn = view.findViewById<CardView>(R.id.userListDLTbtn)
        val userListEditBTN = view.findViewById<CardView>(R.id.userListEditBTN)

        userListUserEmail.text = user.uemail
        userListAddedDate.text = user.tel
        userListUsrName.text = user.uname



        userListEditBTN.setOnClickListener {
            userProfileiew(user)
        }
        theUserListCard.setOnClickListener {
            userCardClicked(user)
        }
    }
}