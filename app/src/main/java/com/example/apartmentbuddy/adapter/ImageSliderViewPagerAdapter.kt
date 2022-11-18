package com.example.apartmentbuddy.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.apartmentbuddy.R


class ImageSliderViewPagerAdapter(val context: Context, private val imageList: List<Uri>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)
        val imageView: ImageView = itemView.findViewById<View>(R.id.imageSliderItem) as ImageView
        Glide.with(context)
            .load(imageList[position])
            .into(imageView)
        val vp = container as ViewPager
        vp.addView(itemView, 0)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}
