package com.example.joyceapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.joyceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // 1. 바인딩 하기
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. 각각의 프레그먼트 변수 선언
        val jsonfragment = JsonFragment()
        val xmlfragment = XmlFragment()
        val bundle = Bundle() // 프레그먼트에 값을 전달하기 위한 Bundle

        // 3. 서치 버튼 클릭 시 이벤트가 일어나도록 설정
        binding.btnSearch.setOnClickListener {
            // 3-1. 전달하고자 하는 값을 Bundle에 넣어줌
            bundle.putString("searchYear", binding.edtYear.text.toString())

            // 3-2. 처리를 위한 이프문
            if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) { // json 선택 시
                jsonfragment.arguments = bundle // 3-3. Bundle에 담긴 값 전달
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, jsonfragment) // activity_content가 json 프레그먼트로 대체
                    .commit()
            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) { // xml 선택 시
                xmlfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment) // activity_content가 xml 프레그먼트로 대체
                    .commit()
            }
        }
    }
}