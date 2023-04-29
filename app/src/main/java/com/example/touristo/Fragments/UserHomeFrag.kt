package com.example.touristo.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.adapter.UserHomeRVAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.Villa
import com.example.touristo.repository.VillaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserHomeFrag : Fragment() {
    private lateinit var adapter: UserHomeRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var db : TouristoDB
    private var fragmentListener: FragmentListenerUserIndex? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_user_home, container, false)

         //Access the views in the Fragment's layout
        recyclerView=view.findViewById(R.id.rvUIndexHome)
        initRecycler()
        val button = view.findViewById<ImageView>(R.id.imgUIndexPropic)
        val imgFavourites = view.findViewById<ImageView>(R.id.imgUIndexFav)
        button.setOnClickListener {
            fragmentListener?.onFragmentPropicButtonClick()
        }
        imgFavourites.setOnClickListener{
            fragmentListener?.onFragmentFavouriteButtonClick()
        }

        return view
    }


    private fun initRecycler(){
        GlobalScope.launch(Dispatchers.IO){
            // Get an instance of the TouristoDB database
            db = TouristoDB.getInstance(requireContext().applicationContext)

            // Get the UserDao from the database
            val villaDao = db.villaDao()
            val villaRepo = VillaRepository(villaDao, Dispatchers.IO)
            recyclerView.layoutManager= LinearLayoutManager(context)
            adapter = UserHomeRVAdapter{
                selectedItem:Villa->listItemClicked(selectedItem)
            }
            recyclerView.adapter = adapter
            adapter.setList(villaRepo.getAllVilla())
            GlobalScope.launch (Dispatchers.Main){

                adapter.notifyDataSetChanged()
            }

        }
    }
    private fun listItemClicked(villa: Villa){
        fragmentListener?.onItemClickedHome(villa)
    }
    // the below method is for communication from parent activity to fragments
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListenerUserIndex) {
            fragmentListener = context
        } else {
            throw RuntimeException("$context must implement FragmentListenerUserIndex")
        }
    }

}