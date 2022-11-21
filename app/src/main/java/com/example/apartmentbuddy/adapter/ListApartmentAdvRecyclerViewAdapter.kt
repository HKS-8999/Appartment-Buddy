package com.example.apartmentbuddy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.controller.AdvertisementController
import com.example.apartmentbuddy.model.Apartment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import me.relex.circleindicator.CircleIndicator

/**
 * Credits for circle indicator: https://medium.com/@mandvi2346verma/image-slider-with-dot-indicators-using-viewpager-firebase-kotlin-android-735968da76f6
 */
class ListApartmentAdvRecyclerViewAdapter(
    private var listings: MutableList<Apartment>,
    private val bottomNavValue: String
) : RecyclerView.Adapter<ListApartmentAdvRecyclerViewAdapter.ViewHolder>() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ImageSliderViewPagerAdapter
    lateinit var context: Context
    lateinit var indicator: CircleIndicator
    lateinit var bookmark: FloatingActionButton
    lateinit var bookmarkRemove: FloatingActionButton
    lateinit var delete: FloatingActionButton

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_apartments, parent, false)

        bookmark = view.findViewById(R.id.bookmark)
        bookmarkRemove = view.findViewById(R.id.bookmark_remove)
        delete = view.findViewById(R.id.delete)

        when (bottomNavValue) {
            "myPosts" -> {
                view.findViewById<FloatingActionButton>(R.id.edit)?.visibility = View.VISIBLE
                view.findViewById<FloatingActionButton>(R.id.delete)?.visibility = View.VISIBLE
                view.findViewById<FloatingActionButton>(R.id.bookmark)?.visibility = View.INVISIBLE
            }
        }

        viewPager = view.findViewById(R.id.idViewPager)
        indicator = view.findViewById(R.id.indicator)
        context = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val advertisementItem = listings[position]

        viewPagerAdapter = ImageSliderViewPagerAdapter(context, advertisementItem.images)
        viewPager.adapter = viewPagerAdapter
        indicator.setViewPager(viewPager)

        holder.unit.text = advertisementItem.unitNumber
        holder.description.text = advertisementItem.description
        holder.bedrooms.text = advertisementItem.noOfBedrooms.toString()
        holder.bathrooms.text = advertisementItem.noOfBathrooms.toString()
        holder.rent.text = advertisementItem.rent.toString()
        holder.startDate.text = advertisementItem.startDate
        holder.contact.text = advertisementItem.contact

        //TODO(): Compare with loggedIn user's ID
        val loggedInUser = advertisementItem.uid
        if (advertisementItem.bookmarkUserList?.map { string ->
                string.replace("[", "").replace("]", "")
            }?.contains(loggedInUser) == true) {
            holder.bookmarkRemove.visibility = View.VISIBLE
        }

        //Bookmark the post
        bookmark.setOnClickListener {
            Snackbar.make(it, "Post Saved For Later", 2000).show()
            AdvertisementController().addUserToBookmarkList(
                advertisementItem.documentId,
                //TODO(): Pass loggedIn USER ID
                advertisementItem.uid
            )
            holder.bookmarkRemove.visibility = View.VISIBLE
            notifyDataSetChanged()
        }

        //Remove Bookmark
        bookmarkRemove.setOnClickListener {
            Snackbar.make(it, "Bookmark Removed", 2000).show()
            AdvertisementController().removeUserToBookmarkList(
                advertisementItem.documentId,
                //TODO(): Pass loggedIn USER ID
                advertisementItem.uid
            )
            holder.bookmarkRemove.visibility = View.INVISIBLE
            if (bottomNavValue == "bookmark") {
                listings.remove(advertisementItem)
            }
            notifyDataSetChanged()
        }

        delete.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete Apartment Listing?")
                .setMessage("Are you sure you want to delete this listing?")

                .setNegativeButton("Cancel") { _, _ ->
                }
                .setPositiveButton("Delete") { dialog, which ->
                    FirebaseFirestore.getInstance().collection("apartments").document(advertisementItem.documentId)
                        .delete()
                        .addOnSuccessListener { Toast.makeText(context, "Listing deleted!", Toast.LENGTH_SHORT)
                            .show()
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener { Toast.makeText(context, "Unable to delete listing! Try again", Toast.LENGTH_LONG)
                            .show() }
                }
                .show()

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listings.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val unit: TextView = itemView.findViewById(R.id.apartmentUnitNumber)
        val description: TextView = itemView.findViewById(R.id.apartmentDescription)
        val bedrooms: TextView = itemView.findViewById(R.id.noOfbedrooms)
        val bathrooms: TextView = itemView.findViewById(R.id.noOfBathrooms)
        val rent: TextView = itemView.findViewById(R.id.apartmentRent)
        val startDate: TextView = itemView.findViewById(R.id.apartmentAvailability)
        val contact: TextView = itemView.findViewById(R.id.apartmentContact)
        val bookmark: FloatingActionButton = itemView.findViewById(R.id.bookmark)
        val bookmarkRemove: FloatingActionButton = itemView.findViewById(R.id.bookmark_remove)
    }
}