package com.example.memorialplace.activities

import android.os.Bundle
import com.example.memorialplace.R
import com.example.memorialplace.databinding.ActivityMapBinding
import com.example.memorialplace.models.MemorialPlaceModel
import com.example.memorialplace.utill.BindingActivity
import com.example.memorialplace.utill.parcelable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : BindingActivity<ActivityMapBinding>(R.layout.activity_map), OnMapReadyCallback {

    private var mMemorialPlaceDetails: MemorialPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            mMemorialPlaceDetails = intent.parcelable(MainActivity.EXTRA_PLACE_DETAILS)
        }
        if (mMemorialPlaceDetails != null) {
            setSupportActionBar(binding.toolbarMap)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = mMemorialPlaceDetails!!.title

            binding.toolbarMap.setNavigationOnClickListener {
                onBackPressed()
            }

            val supportMapFragment: SupportMapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val position = LatLng(mMemorialPlaceDetails!!.latitude, mMemorialPlaceDetails!!.longitude)

        googleMap!!.addMarker(
            MarkerOptions().position(position).title(mMemorialPlaceDetails!!.location)
        )

        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, 15f)
        googleMap.animateCamera(newLatLngZoom)
    }
}