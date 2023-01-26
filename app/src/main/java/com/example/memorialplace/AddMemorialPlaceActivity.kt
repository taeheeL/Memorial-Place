package com.example.memorialplace

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.memorialplace.databinding.ActivityAddMemorialPlaceBinding
import com.example.memorialplace.utill.BindingActivity
import timber.log.Timber

class AddMemorialPlaceActivity :
    BindingActivity<ActivityAddMemorialPlaceBinding>(R.layout.activity_add_memorial_place) {
    private val TAG = this.javaClass.simpleName
    //콜백 인스턴스 생성
    private val callback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            finish()
            Timber.e(TAG, "뒤로가기 클릭")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarAddPlace.setNavigationOnClickListener {
            onBackPressed()
            // TODO deprecated 된 거 고치기
            this.onBackPressedDispatcher.addCallback(this, callback)
        }

    }


}