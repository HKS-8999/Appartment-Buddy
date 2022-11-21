package com.example.apartmentbuddy.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.adapter.CarouselAdapter
import com.example.apartmentbuddy.databinding.FragmentPostItemBinding
import com.example.apartmentbuddy.model.Advertisement
import com.example.apartmentbuddy.model.FirebaseAuthUser
import com.example.apartmentbuddy.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PostItemFragment(advertisementItem: Advertisement?) : Fragment() {
    val advertisement: Item = advertisementItem as Item
    private lateinit var bottomNavValue: String

    private lateinit var binding: FragmentPostItemBinding
    private lateinit var postItemButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var conditionEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var imageUploadButton: ImageButton

    private val db = FirebaseFirestore.getInstance()
    private val itemCollection = db.collection("items")
    private var documentId: String? = null

    private var selectedImages = ArrayList<Uri>()
    private val adapter = CarouselAdapter(selectedImages)

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            postItemButton.isEnabled = false
            for (uri in uris) {
                if (uri != null) {
                    uploadImageToFirebase(uri)
                }
            }
            postItemButton.isEnabled = true
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostItemBinding.inflate(layoutInflater)
        binding.carouselRecyclerview.adapter = adapter
        binding.carouselRecyclerview.apply {
            setInfinite(true)
        }
        bottomNavValue = arguments?.get("bottomNavValue").toString()
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
        imageUploadButton = view.findViewById(R.id.addImages)

        //If the user is editing the existing post
        if (null != advertisement && advertisement.documentId.isNotBlank()) {
            documentId = advertisement.documentId
            titleEditText.setText(advertisement.title)
            descriptionEditText.setText(advertisement.description)
            conditionEditText.setText(advertisement.condition)
            priceEditText.setText(advertisement.price.toString())
            categoryEditText.setText(advertisement.category)
            addressEditText.setText(advertisement.address)
            contactEditText.setText(advertisement.contact)
            selectedImages = advertisement.photos
            binding.carouselRecyclerview.adapter = CarouselAdapter(selectedImages)
            binding.carouselRecyclerview.apply {
                setInfinite(true)
            }
        }

        postItemButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val condition = conditionEditText.text.toString().trim()
            val price = priceEditText.text.toString().trim().toFloat()
            val category = categoryEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            val userId = FirebaseAuthUser.getUserId().toString()
            val item =
                Item(
                    "",
                    userId,
                    selectedImages,
                    description,
                    "Item",
                    contact,
                    title,
                    condition,
                    price,
                    category,
                    address,
                    mutableListOf()
                )
            documentId?.let { it1 ->
                itemCollection.document(it1).set(item, SetOptions.merge())
                    .addOnSuccessListener { void: Void? ->
                        Toast.makeText(
                            activity, "Successfully posted!", Toast.LENGTH_LONG
                        ).show()

                        val bundle = Bundle()
                        bundle.putString("bottomNavValue", bottomNavValue)
                        val fragment = AdvertisementDisplayFragment()
                        fragment.arguments = bundle
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, AdvertisementDisplayFragment())
                            .commit()
                    }.addOnFailureListener { error ->
                        Toast.makeText(
                            activity, error.message.toString(), Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        binding.cancelButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("bottomNavValue", bottomNavValue)
            val fragment = AdvertisementDisplayFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdvertisementDisplayFragment()).commit()
        }

        imageUploadButton.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("items/$fileName")

            // credits to https://heartbeat.comet.ml/working-with-firebase-storage-in-android-part-1-a789f9eea037 for the following snippet lines 142-165
            refStorage.putFile(fileUri)
                .addOnProgressListener {
                    // notify the user about current progress
                    val completePercent = (it.bytesTransferred / it.totalByteCount) * 100
                    if (completePercent.toInt() % 10 == 0) {
                        Toast.makeText(
                            requireContext(),
                            "Uploading : ${completePercent}% done",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnSuccessListener {
                    refStorage.downloadUrl.addOnSuccessListener { uri ->
                        selectedImages.add(uri)
                        adapter.notifyDataSetChanged()
                    }
                    Toast.makeText(requireContext(), "Image uploaded!", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { ex ->
                    Toast.makeText(
                        activity, "Posting failed due to " + ex.message, Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}