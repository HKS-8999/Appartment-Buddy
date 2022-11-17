package com.example.apartmentbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.model.Item

class ListItemAdvRecyclerViewAdapter(private val listings: List<Item>) :
    RecyclerView.Adapter<ListItemAdvRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val advertisementItem = listings[position]
        //TODO()
        holder.imageView.setImageURI(advertisementItem.images)
        holder.title.text = advertisementItem.title
        holder.description.text = advertisementItem.description
        holder.price.text = advertisementItem.price.toString()
        holder.condition.text = advertisementItem.condition
        holder.category.text = advertisementItem.category
        holder.address.text = advertisementItem.address
        holder.contact.text = advertisementItem.contact
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImages)
        val title: TextView = itemView.findViewById(R.id.itemTitle)
        val description: TextView = itemView.findViewById(R.id.itemDescription)
        val price: TextView = itemView.findViewById(R.id.itemPrice)
        val condition: TextView = itemView.findViewById(R.id.itemCondition)
        val category: TextView = itemView.findViewById(R.id.itemCategory)
        val address: TextView = itemView.findViewById(R.id.itemAddress)
        val contact: TextView = itemView.findViewById(R.id.itemContact)
    }
}