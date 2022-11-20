package com.example.apartmentbuddy.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.ListApartmentAdvRecyclerViewAdapter
import com.example.apartmentbuddy.databinding.FragmentApartmentBinding
import com.example.apartmentbuddy.model.Apartment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * A simple [Fragment] subclass.
 * Use the [ApartmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApartmentFragment : Fragment() {
    private lateinit var binding: FragmentApartmentBinding
    private lateinit var bottomNavValue: String
    var adapter: ListApartmentAdvRecyclerViewAdapter? = null

    private val db = FirebaseFirestore.getInstance()
    private val apartmentCollection = db.collection("apartments")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApartmentBinding.inflate(layoutInflater)
        bottomNavValue = arguments?.get("bottomNavValue").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.advRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        var apartmentList: MutableList<Apartment>? = null
        var documentSnapshot: MutableList<DocumentSnapshot> = mutableListOf()
        GlobalScope.launch(Dispatchers.IO) {
            when (bottomNavValue) {
                "home", "null" -> {
                    apartmentList =
                        mapApartmentDataToView(apartmentCollection.get().await().documents)
                }
                "myPosts" -> {
                    //TODO: Add user ID of logged In user
                    apartmentList =
                        mapApartmentDataToView(
                            apartmentCollection.whereEqualTo("uid", "UID").get().await().documents
                        )
                }
                "bookmark" -> {
                    apartmentCollection.get().await().documents.forEach {
                        val list = it.data?.get("bookmarkUserList") as MutableList<String>
                        //TODO() Replace by logged in user ID
                        if (list.map { string ->
                                string.replace("[", "").replace("]", "")
                            }.contains(it.data?.get("uid"))) {
                            documentSnapshot.add(it)
                        }
                    }
                    apartmentList = mapApartmentDataToView(documentSnapshot)
                }
            }
            withContext(Dispatchers.Main) {
                recyclerView.adapter = apartmentList?.let {
                    ListApartmentAdvRecyclerViewAdapter(
                        it, bottomNavValue
                    )
                }
            }
        }
    }

    private fun mapApartmentDataToView(documents: List<DocumentSnapshot>): MutableList<Apartment>? {
        val apartmentList = mutableListOf<Apartment>()
        for (document in documents) {
            val images: ArrayList<Uri> =
                document.get("photos").toString().replace("[", "").replace("]", "")
                    .split(",").map {
                        Uri.parse(it.trim())
                    } as ArrayList<Uri>

            apartmentList.add(
                Apartment(
                    document.id,
                    document.data?.get("uid").toString(),
                    images,
                    document.data?.get("description").toString(),
                    document.data?.get("type").toString(),
                    document.data?.get("contact").toString(),
                    document.data?.get("bathrooms").toString().toFloat(),
                    document.data?.get("bedrooms").toString().toFloat(),
                    document.data?.get("apartment").toString(),
                    document.data?.get("rent").toString().toFloat(),
                    document.data?.get("availability").toString(),
                    document.data?.get("bookmarkUserList") as MutableList<String>
                )
            )
        }
        return apartmentList
    }
}