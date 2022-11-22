package com.example.apartmentbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [complain_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class complain_home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

            override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
                val views= inflater.inflate(R.layout.fragment_complain_home, container, false)

                // Accessing Save button from Fragment 2
                val back: Button = views.findViewById(R.id.complain_back)
                val new_complain: CardView = views.findViewById(R.id.newComplain)

                new_complain.setOnClickListener {
                    views.findNavController().navigate(complain_homeDirections.actionFragmentComplainHomeToFragmentComplainForm())
                }
                return views
        }

    }

