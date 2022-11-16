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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApartmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.advRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ListApartmentAdvRecyclerViewAdapter(ApartmentDataSource().getApartmentList())
    }
}