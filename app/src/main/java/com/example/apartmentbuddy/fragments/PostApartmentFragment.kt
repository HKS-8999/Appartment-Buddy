package com.example.apartmentbuddy.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import com.example.apartmentbuddy.databinding.FragmentPostApartmentBinding

class PostApartmentFragment : Fragment() {
    private lateinit var binding: FragmentPostApartmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostApartmentBinding.inflate(layoutInflater)
        return binding.root
    }
}