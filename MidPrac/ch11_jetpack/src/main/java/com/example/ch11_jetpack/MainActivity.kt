package com.example.ch11_jetpack

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch11_jetpack.OneFragment
import com.example.ch11_jetpack.ThreeFragment
import com.example.ch11_jetpack.TwoFragment
import com.example.ch11_jetpack.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    // 뷰 바인딩 전역 변수 선언
    lateinit var binding : ActivityMainBinding

    // 2. 어댑터 클래스 정의
    // FragmentStateAdapter 상속
    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        // 2-1. 프래그먼트 리스트로 가져오기
        val fragments : List<Fragment>
        // 2-2. 초기화
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
        // 2-3. 프래그먼트 몇개인지 반환
        override fun getItemCount(): Int {
            return fragments.size
        }
        // 2-4. 프래그먼트 리턴
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 뷰 페이저에 프래그먼트를 담기 위해 어댑터 클래스 생성 -> 위에서 정의
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        // 3. 머터리얼 탭 추가 (뷰 페이저와 연결)
        TabLayoutMediator(binding.tabs, binding.viewpager) {
                tab, position ->
            tab.text = "TAB ${position+1}"
        }.attach()
    }
}