package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.ListApartmentAdvRecyclerViewAdapter
import com.example.apartmentbuddy.adapter.ListItemAdvRecyclerViewAdapter
import com.example.apartmentbuddy.databinding.FragmentApartmentBinding
import com.example.apartmentbuddy.persistence.ApartmentDataSource

/**
 * A simple [Fragment] subclass.
 * Use the [ApartmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApartmentFragment : Fragment() {
    private lateinit var binding: FragmentApartmentBinding
    private lateinit var bottomNavValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApartmentBinding.inflate(layoutInflater)
        bottomNavValue = arguments?.get("bottomNavValue").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.advRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        if (bottomNavValue == "home" || bottomNavValue == "null") {
            recyclerView.adapter =
                ListApartmentAdvRecyclerViewAdapter(ApartmentDataSource().getAllApartmentList())
        } else if (bottomNavValue == "myPosts") {
            recyclerView.adapter =
                ListApartmentAdvRecyclerViewAdapter(ApartmentDataSource().getMyApartmentsAdvertisement())
        } else if (bottomNavValue == "bookmark") {
            recyclerView.adapter =
                ListApartmentAdvRecyclerViewAdapter(ApartmentDataSource().getBookmarkedAdvertisement())
        }
    }
}