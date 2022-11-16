package com.example.apartmentbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.data.Item
import com.example.apartmentbuddy.databinding.FragmentPostItemBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PostItemFragment : Fragment() {
    private lateinit var binding: FragmentPostItemBinding
    private lateinit var postItemButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var conditionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var contactEditText: EditText
    private val db = FirebaseFirestore.getInstance()
    private val itemCollection = db.collection("items")
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPostItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postItemButton = view.findViewById(R.id.submit)
        titleEditText = view.findViewById(R.id.title)
        descriptionEditText = view.findViewById(R.id.description)
        conditionEditText = view.findViewById(R.id.condition)
        priceEditText = view.findViewById(R.id.price)
        categoryEditText = view.findViewById(R.id.category)
        addressEditText = view.findViewById(R.id.address)
        contactEditText = view.findViewById(R.id.contact)

        postItemButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val condition = conditionEditText.text.toString().trim()
            val price = priceEditText.text.toString().trim().toFloat()
            val category = categoryEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            val userId = "UID"
            val item =
                Item(userId, title, description, condition, price, category, address, contact)
            itemCollection.document().set(item).addOnSuccessListener { void: Void? ->
                Toast.makeText(
                    activity, "Successfully posted!", Toast.LENGTH_LONG
                ).show()
            }.addOnFailureListener { error ->
                Toast.makeText(
                    activity, error.message.toString(), Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdvertisementHomeFragment()).commit()
        }
    }
}