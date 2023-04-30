package com.example.touristo.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.adapter.UserHomeBLAdapter
import com.example.touristo.adapter.UserHomeFVAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modalDTO.FavouriteDTO
import com.example.touristo.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartFrag : Fragment() {
    private lateinit var adapter: UserHomeFVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var db : TouristoDB
    private var fragmentListener: FragmentListenerUserIndex? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerView = view.findViewById(R.id.rvFavouriteListItem)
        initRecycler()
        return view
    }

    private fun initRecycler() {
        lifecycleScope.launch(Dispatchers.IO){
            db = TouristoDB.getInstance(requireContext().applicationContext)
            val favDao = db.favouriteDao()
            val favRepo = FavouriteRepository(favDao, Dispatchers.IO)

            val email = fragmentListener?.getTheUserEmail()
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = UserHomeFVAdapter{
                selectedItem:FavouriteDTO -> listItemChecked(selectedItem)
            }
            recyclerView.adapter = adapter
            adapter.setList(favRepo.getAllFavouriteList(email.toString()))
            lifecycleScope.launch(Dispatchers.Main){

                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun listItemChecked(selectedItem: FavouriteDTO) {
        Toast.makeText(context,selectedItem.added,Toast.LENGTH_SHORT).show()
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