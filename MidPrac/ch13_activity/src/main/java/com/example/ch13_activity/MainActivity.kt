package com.example.ch13_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    // 2. 데이터 저장 될 변수
    var datas : MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 저장 변수 (저장된 변수 불러와서 초기화)
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            mutableListOf<kotlin.String>()
        }

        // 3. 어댑터 추가
        var adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // Myadapter(datas)

        // 4. 레이아웃 매니저 추가
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // 6. 데코레이션 추가
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        // 5. 결과를 전달 받아서 처리
        val requestLauncher: ActivityResultLauncher<Intent>
                = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            it.data!!.getStringExtra("result")?.let{
                if(it != "") {
                    // 아무것도 입력하지 않으면 추가되지 않고 기존 유지
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // 1. 버튼 누르면 액티비티 변경
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            // 1-1. 현재 날짜 시간
            val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))

            // startActivity(intent)
            // 5-1. 결과받아서 반영
            requestLauncher.launch(intent)
        }
    }

    // 7. 상태 저장
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}