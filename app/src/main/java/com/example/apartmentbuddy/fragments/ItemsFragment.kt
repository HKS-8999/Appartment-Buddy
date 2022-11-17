package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.ListItemAdvRecyclerViewAdapter
import com.example.apartmentbuddy.databinding.FragmentItemsBinding
import com.example.apartmentbuddy.persistence.ItemDataSource

/**
 * A simple [Fragment] subclass.
 * Use the [ItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsFragment : Fragment() {
    private lateinit var binding: FragmentItemsBinding
    private lateinit var bottomNavValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsBinding.inflate(layoutInflater)
        bottomNavValue = arguments?.get("bottomNavValue").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.advRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        if (bottomNavValue == "home" || bottomNavValue == "null") {
            recyclerView.adapter =
                ListItemAdvRecyclerViewAdapter(ItemDataSource().getAllItemsList(), bottomNavValue)
        } else if (bottomNavValue == "myPosts") {
            recyclerView.adapter =
                ListItemAdvRecyclerViewAdapter(ItemDataSource().getMyItemsList(), bottomNavValue)
        } else if (bottomNavValue == "bookmark") {
            recyclerView.adapter =
                ListItemAdvRecyclerViewAdapter(
                    ItemDataSource().getBookmarkedItemsList(),
                    bottomNavValue
                )
        }
    }
}