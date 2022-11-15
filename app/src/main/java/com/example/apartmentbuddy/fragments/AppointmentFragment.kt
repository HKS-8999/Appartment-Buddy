package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.AppointmentAdapter
import com.example.apartmentbuddy.databinding.FragmentAppointmentBinding
import com.example.apartmentbuddy.model.AppointmentData

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Appointment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var nameList: MutableList<AppointmentData> = mutableListOf();
    private lateinit var sampleAdapter : AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        /**  Reference : https://medium.com/@ezatpanah/recyclerview-in-android-with-example-in-depth-guide-94462a6b573b  */
        sampleAdapter = AppointmentAdapter(nameList)
        sampleAdapter.notifyDataSetChanged()
        binding.apply {
            rvMain.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = sampleAdapter;
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(){
        nameList.add(AppointmentData("Saif","10-12-2022","9am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-12-2022","9am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10am"))

        nameList.add(AppointmentData("Saif","10-13-2022","10am"))
        nameList.add(AppointmentData("Saif","10-13-2022","10:30am"))



    }
}