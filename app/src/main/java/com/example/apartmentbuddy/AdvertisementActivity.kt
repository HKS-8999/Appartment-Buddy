package com.example.apartmentbuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.apartmentbuddy.databinding.ActivityAdvertisementBinding
import com.example.apartmentbuddy.fragments.AdvertisementHomeFragment
import com.example.apartmentbuddy.fragments.PostApartmentFragment

class AdvertisementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdvertisementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Display Advertisement Home Page
        replaceFragment(AdvertisementHomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.adv_home -> replaceFragment(AdvertisementHomeFragment())
                R.id.adv_newPost -> replaceFragment(PostApartmentFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (null != fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, fragment)
            transaction.commit()
        }
    }
}