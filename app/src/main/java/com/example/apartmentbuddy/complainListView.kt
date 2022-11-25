package com.example.apartmentbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.adapter.AppointmentRecyclerAdapter
import com.example.apartmentbuddy.adapter.ComplainAdapter
import com.example.apartmentbuddy.fragments.ShowAppointmentDirections
import com.example.apartmentbuddy.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class complainListView : Fragment() {
    private val complains  = Complains()
    private val user_id : String = "kathan@gmail.com"
    private lateinit var list : MutableList<Complalistdata>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: ComplainAdapter

    private val db = FirebaseFirestore.getInstance()
    private lateinit var dataReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var views= inflater.inflate(R.layout.fragment_complain_list_view, container, false)
        recyclerView = views.findViewById(R.id.complain_list_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        list = mutableListOf()
        recyclerAdapter = ComplainAdapter(ComplainList.getAllComplains())
        recyclerView.adapter = recyclerAdapter

        val back: Button = views.findViewById(R.id.complainlist_back)
        back.setOnClickListener {
            views.findNavController().navigate(complainListViewDirections.actionComplainListViewToFragmentComplainHome())
        }
        return views

    }






}