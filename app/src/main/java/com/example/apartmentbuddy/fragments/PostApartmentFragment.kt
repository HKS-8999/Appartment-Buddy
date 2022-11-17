package com.example.apartmentbuddy.fragments

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.InputType
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
import com.example.apartmentbuddy.data.Apartment
import com.example.apartmentbuddy.databinding.FragmentPostApartmentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class PostApartmentFragment : Fragment() {
    private lateinit var binding: FragmentPostApartmentBinding
    private lateinit var postApartmentButton: Button
    private lateinit var bathroomsEditText: EditText
    private lateinit var bedroomsEditText: EditText
    private lateinit var apartmentEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var rentEditText: EditText
    private lateinit var availabilityEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var imageUploadButton: ImageButton

    private val db = FirebaseFirestore.getInstance()
    private val apartmentCollection = db.collection("apartments")
    private val auth = Firebase.auth

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {uri ->
        if (uri != null) {
            uploadImageToFirebase(uri)
        } else {
            // insert code for toast showing no media selected
        }
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostApartmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postApartmentButton = view.findViewById(R.id.submit)
        bathroomsEditText = view.findViewById(R.id.bathrooms)
        bedroomsEditText = view.findViewById(R.id.bedrooms)
        apartmentEditText = view.findViewById(R.id.apartment)
        descriptionEditText = view.findViewById(R.id.description)
        rentEditText = view.findViewById(R.id.rent)
        availabilityEditText = view.findViewById(R.id.availability)
        contactEditText = view.findViewById(R.id.contact)
        imageUploadButton = view.findViewById(R.id.addImages)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // credit to https://stackoverflow.com/a/62139866 for calendar with edit text solution click disable for following two lines
        availabilityEditText.inputType = InputType.TYPE_NULL;
        availabilityEditText.keyListener = null;


        availabilityEditText.setOnClickListener {
            val datePickDialog = DatePickerDialog(requireActivity(),
                { view, pickYear, pickMonth, pickDay ->
                    availabilityEditText.setText("$pickYear/${pickMonth + 1}/$pickDay")
                }, year, month, day
            )
            datePickDialog.show()
        }

        postApartmentButton.setOnClickListener {
            val bedrooms = bedroomsEditText.text.toString().trim().toFloat()
            val bathrooms = bathroomsEditText.text.toString().trim().toFloat()
            val apartment = apartmentEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val rent = rentEditText.text.toString().trim().toFloat()
            val availability = availabilityEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            val userId = "UID"
            val ad =
                Apartment(
                    userId,
                    bedrooms,
                    bathrooms,
                    apartment,
                    description,
                    rent,
                    availability,
                    contact
                )
            apartmentCollection.document().set(ad).addOnSuccessListener { void: Void? ->
                Toast.makeText(
                    activity, "Successfully posted!", Toast.LENGTH_LONG
                ).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AdvertisementHomeFragment()).commit()
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

        imageUploadButton.setOnClickListener {
            getContent.launch("image/*")
        }

    }


    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("apartments/$fileName")

            // credits to https://heartbeat.comet.ml/working-with-firebase-storage-in-android-part-1-a789f9eea037 for the following snippet
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
                    // file upload complered
                    Toast.makeText(requireContext(), "Image uploaded!", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener {
                    // file upload failed̥
                    it.printStackTrace()
                }
        }
    }

}