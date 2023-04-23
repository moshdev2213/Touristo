package com.example.touristo.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.touristo.R
import com.example.touristo.adapter.UserHomeRVAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.repository.UserRepository
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

    //the below method also can be used to directly access the layout file
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    fun initRecycler(){
        GlobalScope.launch(Dispatchers.IO){
            // Get an instance of the TouristoDB database
            db = TouristoDB.getInstance(requireContext().applicationContext)

            // Get the UserDao from the database
            val villaDao = db.villaDao()
            val villaRepo = VillaRepository(villaDao, Dispatchers.IO)
//            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager= LinearLayoutManager(context)
            adapter = UserHomeRVAdapter()
            recyclerView.adapter = adapter

            adapter.setList(villaRepo.getAllVilla())
            adapter.notifyDataSetChanged()
        }



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