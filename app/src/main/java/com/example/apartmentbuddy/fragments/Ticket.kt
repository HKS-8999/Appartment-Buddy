package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.TicketAdapter
import com.example.apartmentbuddy.model.Tickets
import com.google.firebase.firestore.*

open class Ticket : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var list: MutableList<Tickets>
    lateinit var adapter: TicketAdapter
    private val db = FirebaseFirestore.getInstance()
    private val ticketCollection = db.collection("tickets")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)

        val myToolbar: Toolbar = view.findViewById(R.id.toolbar) as Toolbar
        myToolbar.inflateMenu(R.menu.tickets)
        myToolbar.title = "Tickets"
        myToolbar.setTitleTextAppearance(this.context,R.style.CustomActionBarStyle)
        myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        myToolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigate(NewAppointmentDirections.actionNewAppointmentToAppointmentHome())
        }
        myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.pending_tickets -> {
                    // TODO: Navigate to HOME PAGE
                    view.findNavController()
                        .navigate(R.id.action_ticket_to_pendingTickets)
                    true
                }
                else -> false
            }
        }

        return view
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
        ticketCollection.addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null) {
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!) {
                    if(dc.type == DocumentChange.Type.ADDED) {
                        val name: String? = dc.document.getString("name")
                        val status: String? = dc.document.getString("status")
                        val date: String? = dc.document.getString("time")
                        list.add(Tickets(name, status, date, dc.document.id));

                    }
                    println(list)
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