package com.example.memorialplace.adaptors

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memorialplace.databinding.ItemMemorialPlaceBinding
import com.example.memorialplace.models.MemorialPlaceModel

open class MemorialPlacesAdaptor(
    private val context: Context,
    private var list: ArrayList<MemorialPlaceModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMemorialPlaceBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.onBind(list, position)
        }
    }

    private class MyViewHolder(private var binding: ItemMemorialPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(list: ArrayList<MemorialPlaceModel>, position: Int) {
            binding.ivPlaceImage.setImageURI(Uri.parse(list[position].image))
            binding.tvTitle.text = list[position].title
            binding.tvDescription.text = list[position].description
        }
    }


}