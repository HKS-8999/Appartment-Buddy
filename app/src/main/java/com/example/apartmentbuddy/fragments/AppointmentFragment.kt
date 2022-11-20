package com.example.apartmentbuddy.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.AppointmentAdapter
import com.example.apartmentbuddy.databinding.FragmentAppointmentBinding
import com.example.apartmentbuddy.model.AppointmentData
import com.google.firebase.firestore.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Appointment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var nameList: MutableList<AppointmentData> = mutableListOf();
    var ansList: MutableList<AppointmentData> = mutableListOf();
    private lateinit var sampleAdapter : AppointmentAdapter
    private val db = FirebaseFirestore.getInstance()
    private val appointmentCollection = db.collection("appointment")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventChangeListener()
        /**  Reference : https://medium.com/@ezatpanah/recyclerview-in-android-with-example-in-depth-guide-94462a6b573b  */
        sampleAdapter = AppointmentAdapter(nameList)
        binding.apply {
            rvMain.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = sampleAdapter;
            }
        }

    }

    private fun EventChangeListener() {
        appointmentCollection.addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null) {
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!) {
                    if(dc.type == DocumentChange.Type.ADDED) {
                        val name: String? = dc.document.getString("name")
                        val date: String? = dc.document.getString("date")
                        val time: String? = dc.document.getString("time")
                        nameList.add(AppointmentData(name,date,time));
                        println(nameList)

                    }
                    sampleAdapter.notifyDataSetChanged()

                }
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}