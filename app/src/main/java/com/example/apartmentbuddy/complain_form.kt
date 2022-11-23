package com.example.apartmentbuddy

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.apartmentbuddy.adapter.CarouselAdapter
import com.example.apartmentbuddy.databinding.FragmentComplainFormBinding
import com.example.apartmentbuddy.fragments.AdvertisementDisplayFragment
import com.example.apartmentbuddy.model.Complain
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class complain_form : Fragment() {
    private lateinit var binding: FragmentComplainFormBinding
    private lateinit var postComplainButton: Button
    private lateinit var unitNumberEditText: EditText
    private lateinit var categoryComplainEditText: EditText
    private lateinit var subjectComplainEditText: EditText
    private lateinit var descriptionComplainEditText: EditText
    private lateinit var imageUploadButton: ImageButton

    private val db = FirebaseFirestore.getInstance()
    private val complainCollection = db.collection("complain")
    private val auth = Firebase.auth
    private val selectedImages = ArrayList<Uri>()
    private val adapter = CarouselAdapter(selectedImages)

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            postComplainButton.isEnabled = false
            for (uri in uris) {
                if (uri != null) {
                    uploadImageToFirebase(uri)
                }
            }
            postComplainButton.isEnabled = true
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComplainFormBinding.inflate(layoutInflater)
        binding.carouselRecyclerview.adapter = adapter
        binding.carouselRecyclerview.apply {
            setInfinite(true)
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postComplainButton = view.findViewById(R.id.submitComplain)
        unitNumberEditText = view.findViewById(R.id.unitNumber)
        categoryComplainEditText = view.findViewById(R.id.categoryComplain)
        subjectComplainEditText = view.findViewById(R.id.subjectComplain)
        descriptionComplainEditText = view.findViewById(R.id.descriptionComplain)
        imageUploadButton = view.findViewById(R.id.addComplainImages)

        val simpleDate = SimpleDateFormat("dd/M/yyyy ")
        val currentDate = simpleDate.format(Date())

        val calendar = Calendar.getInstance()
        val ticketid= calendar.timeInMillis.toString()

        postComplainButton.setOnClickListener {
            val unitnumber = unitNumberEditText.text.toString().trim()
            val category = categoryComplainEditText.text.toString().trim()
            val subject = subjectComplainEditText.text.toString().trim()
            val description = descriptionComplainEditText.text.toString().trim()
            val userId = "dhruv@gmail.com"
            val status ="No responded"
            val name ="Dhruvit"

            val complains =
                Complain(
                    userId,
                    selectedImages,
                    description,
                    subject,
                    currentDate,
                    category,
                    unitnumber,
                    status,
                    name,
                    ticketid
                )

            complainCollection.document().set(complains).addOnSuccessListener { void: Void? ->
                Toast.makeText(
                    activity, "Successfully posted!", Toast.LENGTH_LONG)
//                ).show()
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, AdvertisementDisplayFragment()).commit()
            }.addOnFailureListener { error ->
                Toast.makeText(
                    activity, error.message.toString(), Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.backComplain.setOnClickListener {
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
