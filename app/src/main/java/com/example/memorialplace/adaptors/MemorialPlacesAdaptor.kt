package com.example.memorialplace.adaptors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.memorialplace.activities.AddMemorialPlaceActivity
import com.example.memorialplace.activities.MainActivity
import com.example.memorialplace.database.DatabaseHandler
import com.example.memorialplace.databinding.ItemMemorialPlaceBinding
import com.example.memorialplace.models.MemorialPlaceModel
import java.util.jar.Manifest

open class MemorialPlacesAdaptor(
    private val context: Context,
    private var list: ArrayList<MemorialPlaceModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMemorialPlaceBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.onBind(list, position)

            holder.itemView.setOnClickListener {
                onClickListener?.onClick(position, list[position])
            }
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
    fun notifyEditItem(resultLauncher: ActivityResultLauncher<Intent>, position: Int, requestCode: Int) {
        val intent = Intent(context, AddMemorialPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        resultLauncher.launch(intent)

        notifyItemChanged(position)
    }

    fun removeAt(position: Int) {
        val dbHandler = DatabaseHandler(context)
        val isDelete = dbHandler.deleteMemorialPlace(list[position])
        if (isDelete > 0) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: MemorialPlaceModel) {

        }
    }


}