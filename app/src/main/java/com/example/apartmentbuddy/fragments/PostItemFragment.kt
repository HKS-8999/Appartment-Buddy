package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apartmentbuddy.databinding.FragmentPostItemBinding

class PostItemFragment : Fragment() {
    private lateinit var binding: FragmentPostItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val items= arrayOf("Condition..", "New", "Used")

        binding = FragmentPostItemBinding.inflate(layoutInflater)
        return binding.root
    }
}