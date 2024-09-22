package com.example.ch13_activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 메인 액티비티에서 보내주는 현재 날짜 시간 받음
        val date = intent.getStringExtra("today")
        binding.date.text = date

        // 3. 백 버튼 추가
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 2. 세이브 버튼 누르면 결과값 갖고 원래 액티비티로 돌아가기
        binding.btnSave.setOnClickListener {
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)

        finish()
        return true
    }
}