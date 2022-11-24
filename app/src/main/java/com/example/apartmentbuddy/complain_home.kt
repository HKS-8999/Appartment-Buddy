package com.example.apartmentbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.example.apartmentbuddy.model.Appointment
import com.example.apartmentbuddy.model.Complains


class complain_home : Fragment() {
    private val complains  = Complains()
    private val user_id : String = "kathan@gmail.com"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views= inflater.inflate(R.layout.fragment_complain_home, container, false)

        // Accessing Save button from Fragment 2

        val viewComplains: CardView = views.findViewById(R.id.ViewComplain)
        viewComplains.setOnClickListener{
            complains.showComplain(user_id)
            viewComplains.findNavController().navigate(complain_homeDirections.actionFragmentComplainHomeToComplainListView())
        }


        val back: Button = views.findViewById(R.id.complain_back)
        val new_complain: CardView = views.findViewById(R.id.newComplain)


        new_complain.setOnClickListener {
            views.findNavController().navigate(complain_homeDirections.actionFragmentComplainHomeToFragmentComplainForm())
        }
        return views
    }

}

