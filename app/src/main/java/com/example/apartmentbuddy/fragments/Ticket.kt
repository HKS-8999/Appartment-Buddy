package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.TicketAdapter
import com.example.apartmentbuddy.model.AppointmentData
import com.example.apartmentbuddy.model.Tickets
import com.google.firebase.firestore.*

class Ticket : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var list: MutableList<Tickets>
    lateinit var adapter: TicketAdapter
    private val db = FirebaseFirestore.getInstance()
    private val appointmentCollection = db.collection("appointment")

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
        adapter = TicketAdapter(requireContext(),list, statusAdapter)
        recyclerView.adapter = adapter
        EventChangeListener()


    }

    private fun EventChangeListener() {
        appointmentCollection.addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null) {
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!) {
                    if(dc.type == DocumentChange.Type.ADDED) {
                        val name: String? = dc.document.getString("name")
                        val status: String? = dc.document.getString("date")
                        val date: String? = dc.document.getString("time")
                        list.add(Tickets(name,status,date));
                        println(list)

                    }
                adapter.notifyDataSetChanged()
                }
            }
        })


    }

    private fun loadData() {
        list.add(Tickets("Saifali", "Not responded", "10am"))
        list.add(Tickets("Saifali", "Open", "4pm"))
        list.add(Tickets("Saifali", "Close", "6am"))

    }


}