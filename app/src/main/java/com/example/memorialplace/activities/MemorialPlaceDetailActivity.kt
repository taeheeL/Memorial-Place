package com.example.memorialplace.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.memorialplace.R
import com.example.memorialplace.databinding.ActivityMemorialPlaceDetailBinding
import com.example.memorialplace.models.MemorialPlaceModel
import com.example.memorialplace.utill.BindingActivity
import com.example.memorialplace.utill.parcelable

class MemorialPlaceDetailActivity :
    BindingActivity<ActivityMemorialPlaceDetailBinding>(R.layout.activity_memorial_place_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var memorialPlaceDetailModel: MemorialPlaceModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            memorialPlaceDetailModel = intent.parcelable(MainActivity.EXTRA_PLACE_DETAILS)
        }

        if (memorialPlaceDetailModel != null) {
            setSupportActionBar(binding.toolbarMemorialPlaceDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = memorialPlaceDetailModel.title

            binding.toolbarMemorialPlaceDetail.setNavigationOnClickListener {
                onBackPressed()
            }

            binding.ivPlaceImage.setImageURI(Uri.parse(memorialPlaceDetailModel.image))
            binding.tvDescription.text = memorialPlaceDetailModel.description
            binding.tvLocation.text = memorialPlaceDetailModel.location

            binding.btnViewOnMap.setOnClickListener{
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, memorialPlaceDetailModel)
                startActivity(intent)
            }
        }
    }
}