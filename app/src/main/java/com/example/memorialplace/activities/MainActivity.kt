package com.example.memorialplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorialplace.R
import com.example.memorialplace.adaptors.MemorialPlacesAdaptor
import com.example.memorialplace.database.DatabaseHandler
import com.example.memorialplace.databinding.ActivityMainBinding
import com.example.memorialplace.models.MemorialPlaceModel
import com.example.memorialplace.utill.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fabAddPlace.setOnClickListener {
            val intent = Intent(this, AddMemorialPlaceActivity::class.java)
            startActivity(intent)
        }
        getMemorialListFromLocalDB()
    }

    private fun setupMemorialPlacesRecyclerView(
        memorialPlaceList: ArrayList<MemorialPlaceModel>
    ) {
        binding.rvMemorialPlacesList.layoutManager = LinearLayoutManager(this)
        binding.rvMemorialPlacesList.setHasFixedSize(true)

        val placesAdaptor = MemorialPlacesAdaptor(this, memorialPlaceList)
        binding.rvMemorialPlacesList.adapter = placesAdaptor
    }

    private fun getMemorialListFromLocalDB() {
        val dbHandler = DatabaseHandler(this)
        val getMemorialPlaceList: ArrayList<MemorialPlaceModel> = dbHandler.getMemorialPlacesList()

        if (getMemorialPlaceList.size > 0) {
            for (i in getMemorialPlaceList) {
                binding.rvMemorialPlacesList.visibility = View.VISIBLE
                binding.tvNoRecordsAvailable.visibility = View.GONE
                setupMemorialPlacesRecyclerView(getMemorialPlaceList)
            }
        } else {
            binding.rvMemorialPlacesList.visibility = View.GONE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }
}