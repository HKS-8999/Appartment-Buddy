package com.example.apartmentbuddy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apartmentbuddy.databinding.ItemRowBinding
/**  Reference : https://medium.com/@ezatpanah/recyclerview-in-android-with-example-in-depth-guide-94462a6b573b  */
class AppointmentAdapter (val items : MutableList<AppointmentData>)
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(){
    private lateinit var binding: ItemRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding=ItemRowBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AppointmentAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount() = items.size
    inner class ViewHolder(itemView : ItemRowBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : AppointmentData){
            binding.apply {
                tvName.text = "Name: " + item.name
                tvDate.text = "Date: " +item.date
                tvTime.text = "Time: " +item.time
            }
        }
    }
}
