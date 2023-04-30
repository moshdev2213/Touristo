package com.example.touristo.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.adapter.UserHomeBLAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookingFrag : Fragment() {
    private lateinit var adapter: UserHomeBLAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var db : TouristoDB
    private var fragmentListener: FragmentListenerUserIndex? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_booking, container, false)
        //Access the views in the Fragment's layout
        recyclerView=view.findViewById(R.id.rvBookingListItem)
        initRecycler()

        return view
    }
    private fun initRecycler(){
        lifecycleScope.launch(Dispatchers.IO) {
            // Get an instance of the TouristoDB database
            db = TouristoDB.getInstance(requireContext().applicationContext)
            val bookingDao = db.bookingDao()
            val bookingRepo = BookingRepository(bookingDao,Dispatchers.IO)

            val theEmail = fragmentListener?.getTheUserEmail()

            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = UserHomeBLAdapter({
                selectedItem: BookingDTO ->listItemClicked(selectedItem)
            },{
                payItem: BookingDTO ->initiatePaySlip(payItem)
            })
            //herachical touch error then put inside the lifecyclescope
            recyclerView.adapter = adapter
            adapter.setList(bookingRepo.getBookingForListView(theEmail.toString()))

            lifecycleScope.launch(Dispatchers.Main){
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun initiatePaySlip(payItem: BookingDTO) {
        fragmentListener?.InitiatePaySlip(payItem)
    }

    private fun listItemClicked(bookingDTO: BookingDTO) {
        fragmentListener?.onBookingItemClicked(bookingDTO)
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