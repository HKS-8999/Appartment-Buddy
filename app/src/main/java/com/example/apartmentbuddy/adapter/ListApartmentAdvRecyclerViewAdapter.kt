package com.example.apartmentbuddy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.apartmentbuddy.R
import com.example.apartmentbuddy.controller.AdvertisementController
import com.example.apartmentbuddy.model.Apartment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import me.relex.circleindicator.CircleIndicator

/**
 * Credits for circle indicator: https://medium.com/@mandvi2346verma/image-slider-with-dot-indicators-using-viewpager-firebase-kotlin-android-735968da76f6
 */
class ListApartmentAdvRecyclerViewAdapter(
    private val listings: List<Apartment>,
    private val bottomNavValue: String
) : RecyclerView.Adapter<ListApartmentAdvRecyclerViewAdapter.ViewHolder>() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ImageSliderViewPagerAdapter
    lateinit var context: Context
    lateinit var indicator: CircleIndicator
    lateinit var bookmark: FloatingActionButton
    lateinit var bookmarkRemove: FloatingActionButton

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_apartments, parent, false)

        bookmark = view.findViewById(R.id.bookmark)
        bookmarkRemove = view.findViewById(R.id.bookmark_remove)

        when (bottomNavValue) {
            "myPosts" -> {
                view.findViewById<FloatingActionButton>(R.id.edit)?.visibility = View.VISIBLE
                view.findViewById<FloatingActionButton>(R.id.delete)?.visibility = View.VISIBLE
                view.findViewById<FloatingActionButton>(R.id.bookmark)?.visibility = View.INVISIBLE
            }
            "bookmark" -> {
                view.findViewById<FloatingActionButton>(R.id.bookmark_remove)?.visibility =
                    View.VISIBLE
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

        //displayRemoveBookmarkFab(advertisementItem.bookmarkUserList, holder)

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
        }

        //Remove Bookmark
        bookmarkRemove.setOnClickListener {
            Snackbar.make(it, "Bookmark Removed", 2000).show()
            AdvertisementController().removeUserToBookmarkList(
                advertisementItem.documentId,
                //TODO(): Pass loggedIn USER ID
                advertisementItem.uid
            )
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

    fun displayRemoveBookmarkFab(list: MutableList<String>?, holder: ViewHolder) {
        //TODO(): Compare with loggedIn user's ID
        val loggedInUser = "Minal"

        if (list?.map { string ->
                string.replace("[", "").replace("]", "")
            }?.contains(loggedInUser) == true) {
            holder.bookmarkRemove.visibility = View.VISIBLE
        }
    }
}