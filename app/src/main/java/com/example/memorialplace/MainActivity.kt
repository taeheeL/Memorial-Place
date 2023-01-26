package com.example.memorialplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.memorialplace.databinding.ActivityMainBinding
import com.example.memorialplace.utill.BindingActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fabAddPlace.setOnClickListener{
            val intent = Intent(this, AddMemorialPlaceActivity::class.java)
            startActivity(intent)
        }
    }
}