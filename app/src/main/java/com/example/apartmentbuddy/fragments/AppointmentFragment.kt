package com.example.apartmentbuddy.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.AppointmentAdapter
import com.example.apartmentbuddy.databinding.FragmentAppointmentBinding
import com.example.apartmentbuddy.model.AppointmentData
import com.google.firebase.firestore.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Appointment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var nameList: MutableList<AppointmentData> = mutableListOf();
    private lateinit var sampleAdapter: AppointmentAdapter
    private val db = FirebaseFirestore.getInstance()
    private val appointmentCollection = db.collection("appointment")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        val myToolbar: Toolbar = binding.toolbar
        myToolbar.inflateMenu(R.menu.appointment_new)
        myToolbar.title = "Appointment"
        myToolbar.setTitleTextAppearance(this.context, R.style.CustomActionBarStyle)
        myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        myToolbar.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_appointment_to_homeAdmin2)
        }
        myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_home -> {
                    findNavController().navigate(R.id.action_appointment_to_homeAdmin2)
                    true
                }
                else -> false
            }
        }
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun EventChangeListener() {
        appointmentCollection.addSnapshotListener(object : EventListener<QuerySnapshot> {
            val current_date = LocalDate.now()
            val formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formatted_date = current_date.format(formatter_date)
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val date = dc.document.getString("date")
                        if (date.toString() > formatted_date.toString()) {
                            val name: String? = dc.document.getString("name")
                            val date: String? = dc.document.getString("date")
                            val time: String? = dc.document.getString("time")
                            nameList.add(AppointmentData(name = name, date = date, time = time));
                            println(nameList)
                        }
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