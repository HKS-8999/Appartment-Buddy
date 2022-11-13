package com.example.apartmentbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Advertisement

class AdvertisementRecyclerViewAdapter(private val listings: List<Advertisement>) :
    RecyclerView.Adapter<AdvertisementRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdvertisementRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_advertisement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AdvertisementRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val advertisementItem = listings[position]
        holder.imageView.setImageURI(advertisementItem.images)
        holder.description.text = advertisementItem.description
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.advImage)
        val description: TextView = itemView.findViewById(R.id.advDescription)
    }
}