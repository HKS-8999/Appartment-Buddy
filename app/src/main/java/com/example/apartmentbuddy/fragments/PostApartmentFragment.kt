package com.example.apartmentbuddy.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.AdvertisementViewPagerAdapter
import com.example.apartmentbuddy.databinding.FragmentPostApartmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PostApartmentFragment : Fragment() {
    private lateinit var binding: FragmentPostApartmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostApartmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdvertisementHomeFragment()).commit()
        }
    }
}