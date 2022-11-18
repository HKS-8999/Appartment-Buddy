package com.example.apartmentbuddy.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apartmentbuddy.adapter.ListItemAdvRecyclerViewAdapter
import com.example.apartmentbuddy.databinding.FragmentItemsBinding
import com.example.apartmentbuddy.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [ItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsFragment : Fragment() {
    private lateinit var binding: FragmentItemsBinding
    private lateinit var bottomNavValue: String

    private val db = FirebaseFirestore.getInstance()
    private val itemCollection = db.collection("items")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsBinding.inflate(layoutInflater)
        bottomNavValue = arguments?.get("bottomNavValue").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.advRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        when (bottomNavValue) {
            "home", "null" -> {
                GlobalScope.launch(Dispatchers.IO) {
                    val itemList = mutableListOf<Item>()
                    itemCollection.get().await().documents.forEach { document ->
                        val images: ArrayList<Uri> =
                            document.get("photos").toString().replace("[", "").replace("]", "")
                                .split(",").map {
                                    Uri.parse(it)
                                } as ArrayList<Uri>

                        itemList.add(
                            Item(
                                document.data?.get("uid").toString(),
                                images,
                                document.data?.get("description").toString(),
                                document.data?.get("type").toString(),
                                document.data?.get("contact").toString(),
                                document.data?.get("title").toString(),
                                document.data?.get("condition").toString(),
                                document.data?.get("price").toString().toFloat(),
                                document.data?.get("category").toString(),
                                document.data?.get("address").toString(),
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter =
                            ListItemAdvRecyclerViewAdapter(
                                itemList,
                                bottomNavValue
                            )
                    }
                }
            }
            "myPosts" -> {
                GlobalScope.launch(Dispatchers.IO) {
                    val itemList = mutableListOf<Item>()
                    //TODO: Add user ID of the user logged In
                    itemCollection.whereEqualTo("uid", "UID").get()
                        .await().documents.forEach { document ->
                            val images: ArrayList<Uri> =
                                document.get("photos").toString().replace("[", "").replace("]", "")
                                    .split(",").map {
                                        Uri.parse(it)
                                    } as ArrayList<Uri>

                            itemList.add(
                                Item(
                                    document.data?.get("uid").toString(),
                                    images,
                                    document.data?.get("description").toString(),
                                    document.data?.get("type").toString(),
                                    document.data?.get("contact").toString(),
                                    document.data?.get("title").toString(),
                                    document.data?.get("condition").toString(),
                                    document.data?.get("price").toString().toFloat(),
                                    document.data?.get("category").toString(),
                                    document.data?.get("address").toString(),
                                )
                            )
                        }
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter =
                            ListItemAdvRecyclerViewAdapter(
                                itemList,
                                bottomNavValue
                            )
                    }
                }
            }
            "bookmark" -> {

            }
        }
    }
}