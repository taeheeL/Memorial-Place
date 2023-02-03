package com.example.memorialplace.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorialplace.R
import com.example.memorialplace.adaptors.MemorialPlacesAdaptor
import com.example.memorialplace.database.DatabaseHandler
import com.example.memorialplace.databinding.ActivityMainBinding
import com.example.memorialplace.models.MemorialPlaceModel
import com.example.memorialplace.utill.BindingActivity
import com.example.memorialplace.utill.SwipeToDeleteCallback
import com.example.memorialplace.utill.SwipeToEditCallback

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fabAddPlace.setOnClickListener {
            val intent = Intent(this, AddMemorialPlaceActivity::class.java)
            resultLauncher.launch(intent)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    getMemorialListFromLocalDB()
                }
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

        placesAdaptor.setOnClickListener(object : MemorialPlacesAdaptor.OnClickListener {
            override fun onClick(position: Int, model: MemorialPlaceModel) {
                val intent = Intent(this@MainActivity, MemorialPlaceDetailActivity::class.java)
                intent.putExtra(EXTRA_PLACE_DETAILS, model)
                startActivity(intent)
            }
        }
        )

        val editSwipeHandler = object : SwipeToEditCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adaptor = binding.rvMemorialPlacesList.adapter as MemorialPlacesAdaptor
                adaptor.notifyEditItem(this@MainActivity, viewHolder.adapterPosition, ADD_PLACE_ACTIVITY_REQUEST_CODE)
            }
        }

        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(binding.rvMemorialPlacesList)

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adaptor = binding.rvMemorialPlacesList.adapter as MemorialPlacesAdaptor

                adaptor.removeAt(viewHolder.adapterPosition)

                getMemorialListFromLocalDB()
            }
        }

        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding.rvMemorialPlacesList)
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

    companion object{
        private const val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PLACE_DETAILS = "extra_place_details"

    }

}