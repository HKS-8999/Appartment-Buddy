package com.example.apartmentbuddy.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Apartment
import com.example.apartmentbuddy.model.Item
import com.example.apartmentbuddy.persistence.ApartmentDataSource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListApartmentAdvRecyclerViewAdapter(
    private val listings: List<Apartment>,
    private val bottomNavValue: String
) : RecyclerView.Adapter<ListApartmentAdvRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_apartments, parent, false)
        if (bottomNavValue == "myPosts") {
            view.findViewById<FloatingActionButton>(R.id.edit).visibility = View.VISIBLE
            view.findViewById<FloatingActionButton>(R.id.delete).visibility = View.VISIBLE
            view.findViewById<FloatingActionButton>(R.id.bookmark).visibility = View.INVISIBLE
        }
        if (bottomNavValue == "bookmark") {
            view.findViewById<FloatingActionButton>(R.id.bookmark_remove).visibility = View.VISIBLE
        }
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val advertisementItem = listings[position]
        //TODO()
        //holder.imageView.setImageURI(advertisementItem.images)
        holder.unit.text = advertisementItem.unitNumber.toString()
        holder.description.text = advertisementItem.description
        holder.bedrooms.text = advertisementItem.noOfBedrooms.toString()
        holder.bathrooms.text = advertisementItem.noOfBathrooms.toString()
        holder.rent.text = advertisementItem.rent.toString()
        holder.startDate.text = advertisementItem.startDate.toString()
        holder.contact.text = advertisementItem.contact
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImages)
        val unit: TextView = itemView.findViewById(R.id.apartmentUnitNumber)
        val description: TextView = itemView.findViewById(R.id.apartmentDescription)
        val bedrooms: TextView = itemView.findViewById(R.id.noOfbedrooms)
        val bathrooms: TextView = itemView.findViewById(R.id.noOfBathrooms)
        val rent: TextView = itemView.findViewById(R.id.apartmentRent)
        val startDate: TextView = itemView.findViewById(R.id.apartmentAvailability)
        val contact: TextView = itemView.findViewById(R.id.apartmentContact)
    }
}