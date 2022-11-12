package com.example.apartmentbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apartmentbuddy.adapter.AdvertisementViewPagerAdapter
import com.example.apartmentbuddy.databinding.ActivityAdvertisementBinding
import com.google.android.material.tabs.TabLayoutMediator

class AdvertisementActivity : AppCompatActivity() {
    private val tabOptionsArray = arrayOf("Apartments", "Items")
    private lateinit var binding: ActivityAdvertisementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.advViewPager
        val advertisementAdapter = AdvertisementViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = advertisementAdapter

        val tabLayout = binding.advTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabOptionsArray.get(position)
        }.attach()
    }
}