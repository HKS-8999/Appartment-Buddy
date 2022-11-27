package com.example.apartmentbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.example.apartmentbuddy.model.ComplainPersistence

class ComplainHome : Fragment() {
    private val complains  = ComplainPersistence()
    private val user_id : String = "dhruv@gmail.com"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views= inflater.inflate(R.layout.fragment_complain_home, container, false)

        val viewComplains: CardView = views.findViewById(R.id.ViewComplain)
        viewComplains.setOnClickListener{
            viewComplains.findNavController().navigate(ComplainHomeDirections.actionFragmentComplainHomeToComplainListView())
        }

        val new_complain: CardView = views.findViewById(R.id.newComplain)

        new_complain.setOnClickListener {
            views.findNavController().navigate(ComplainHomeDirections.actionFragmentComplainHomeToFragmentComplainForm())
        }
        return views
    }
}