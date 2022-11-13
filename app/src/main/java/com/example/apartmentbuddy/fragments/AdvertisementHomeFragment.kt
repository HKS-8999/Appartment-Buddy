package com.example.apartmentbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apartmentbuddy.adapter.AdvertisementViewPagerAdapter
import com.example.apartmentbuddy.databinding.FragmentAdvertisementHomeBinding
import com.example.apartmentbuddy.databinding.FragmentApartmentBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A simple [Fragment] subclass.
 * Use the [AdvertisementHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvertisementHomeFragment : Fragment() {
    private lateinit var binding: FragmentAdvertisementHomeBinding
    private val tabOptionsArray = arrayOf("Apartments", "Items")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvertisementHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.advViewPager
        val advertisementAdapter = AdvertisementViewPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = advertisementAdapter

        val tabLayout = binding.advTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabOptionsArray.get(position)
        }.attach()
    }
}