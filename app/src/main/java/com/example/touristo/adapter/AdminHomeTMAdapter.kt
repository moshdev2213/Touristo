package com.example.touristo.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.collections.ArrayList

class AdminHomeTMAdapter(
    var context: Context,
    private val userProfileView:(User,Bitmap)->Unit,
    private val userCardClicked: (User,Bitmap) -> Unit
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
        holder.itemView.findViewById<CardView>(R.id.userListDLTbtn).setOnClickListener {
            showConfirmationDialog(position,userList[position])
        }
//        holder.itemView.findViewById<CardView>(R.id.userListEditBTN).setOnClickListener {
//            userProfileView(userList[position],bitsMap)
//            updateItem(position)
//        }
        getImageFromFirebase(userList[position]) { bitmap ->

            if (bitmap != null) {
                // do something with the bitmap
                holder.bind(userList[position],userProfileView,userCardClicked,bitmap)
                holder.itemView.findViewById<ImageView>(R.id.shapeableImageView).setImageBitmap(bitmap)
            } else {
                // handle error
            }
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
    fun updateItem(position:Int){
        notifyDataSetChanged()
    }
    private fun showConfirmationDialog(position: Int,user: User) {
        val yesNoDialog = YesNoDialog(context)
        yesNoDialog.yesNoConfirmDialog({
            //do Something
        },{
            GlobalScope.launch(Dispatchers.IO){
                db = TouristoDB.getInstance(context)
                val userDAo = db.userDao()
                val userRepo = UserRepository(userDAo, Dispatchers.IO)
                userRepo.deleteUser(user)
            }
            deleteItem(position)
        })

    }

    private fun getImageFromFirebase(user: User, callback: (Bitmap?) -> Unit) {
//        val progressBuilder = ProgressLoader(context,"Fetching Details","Please Wait......")
//        progressBuilder.startProgressLoader()

        val storageRef = FirebaseStorage.getInstance().reference.child("UserImages")
        storageRef.listAll().addOnSuccessListener { listResult ->
            var bitmap: Bitmap? = null
            for (item in listResult.items) {
                if (item.name == user.propic) {
                    val localFile = File.createTempFile("tempImg", "jpg")
                    item.getFile(localFile).addOnSuccessListener {
                        bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        callback(bitmap)
//                        progressBuilder.dismissProgressLoader()
                    }.addOnFailureListener {
                        callback(null)
//                        progressBuilder.dismissProgressLoader()
                    }
                }
            }
//            progressBuilder.dismissProgressLoader()
        }.addOnFailureListener {
            callback(null)
//            progressBuilder.dismissProgressLoader()
        }
    }

}
class AdminHomeTMViewHolder(private val view: View):RecyclerView.ViewHolder(view){
    fun bind(
        user: User,
        userProfileView: (User, Bitmap) -> Unit,
        userCardClicked:(User, Bitmap)->Unit,
        bitmap: Bitmap){

        val userListUserEmail = view.findViewById<TextView>(R.id.userListUserEmail)
        val userListAddedDate = view.findViewById<TextView>(R.id.userListAddedDate)
        val userListUsrName = view.findViewById<TextView>(R.id.userListUsrName)

        val theUserListCard = view.findViewById<CardView>(R.id.theUserListCard)
        val userListDLTbtn = view.findViewById<CardView>(R.id.userListDLTbtn)
        val userListEditBTN = view.findViewById<CardView>(R.id.userListEditBTN)

        userListUserEmail.text = user.uemail
        userListAddedDate.text = user.tel
        userListUsrName.text = user.uname


        theUserListCard.setOnClickListener {
            userCardClicked(user,bitmap)
        }
        userListEditBTN.setOnClickListener {
            userProfileView(user,bitmap)
        }
    }



}