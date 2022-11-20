package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.TicketAdapter
import com.example.apartmentbuddy.model.Tickets

class Ticket : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var list: MutableList<Tickets>
    lateinit var adapter: TicketAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvMain)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        val language = resources.getStringArray(R.array.status_options)
        val statusAdapter = ArrayAdapter(requireContext(), R.layout.list_item,language)
        list = mutableListOf()
        loadData()
        adapter = TicketAdapter(requireContext(),list, statusAdapter)
        recyclerView.adapter = adapter

    }

    private fun loadData() {
        list.add(Tickets("Saifali", "Not responded", "10am"))
        list.add(Tickets("Saifali", "Open", "4pm"))
        list.add(Tickets("Saifali", "Close", "6am"))

    }


}