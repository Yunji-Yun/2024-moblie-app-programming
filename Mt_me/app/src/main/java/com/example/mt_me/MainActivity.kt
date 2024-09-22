package com.example.mt_me

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mt_me.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import java.security.MessageDigest

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var sharedPreferences: SharedPreferences
    lateinit var headerView: View

    // viewpager class
//    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
//        val fragments : List<Fragment>
//        init {
//            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
//        }
//        override fun getItemCount(): Int {
//            return fragments.size
//        }
//
//        override fun createFragment(position: Int): Fragment {
//            return fragments[position]
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // drawer
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // viewpager
        // binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        // tab
//        TabLayoutMediator(binding.tabs, binding.viewpager) {
//                tab, position ->
//            if(position == 0) {
//                tab.text = "산 정보 찾기"
//            } else if(position == 1) {
//                tab.text = "식물 정보 찾기"
//            } else {
//                tab.text = "유튜브 찾기"
//            }
//
//        }.attach()

        // navigation
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        // sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreferences.getString("color", "#0FA958")
        binding.mtmeTitle.setBackgroundColor(Color.parseColor(color))

        val idstr = sharedPreferences.getString("id", "Mt. Me")
        binding.mtmeTitle.text = idstr

        val size = sharedPreferences.getString("size", "16.0f")
        binding.mtmeTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

        val visible = sharedPreferences.getString("visible", "visible")
        binding.mtmeTitle.visibility = when (visible) {
            "visible" -> View.VISIBLE
            "gone" -> View.GONE
            else -> View.VISIBLE
        }

        // frame, bundle
        val onefragment = OneFragment()
        val twofragment = TwoFragment()
        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            if(MyApplication.checkAuth()) {
                bundle.putString("searchName", binding.edtName.text.toString())

                if (binding.rGroup.checkedRadioButtonId == R.id.rbMt) { // 산 정보 찾기 선택
                    onefragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, onefragment)
                        .commit()
                } else if (binding.rGroup.checkedRadioButtonId == R.id.rbTree) { // 식물 정보 찾기 선택
                    twofragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, twofragment)
                        .commit()
                }
            }
            else {
                Toast.makeText(this, "로그인 후에 편하게 서비스를 이용하세요!", Toast.LENGTH_LONG).show()
            }

//            bundle.putString("searchName", binding.edtName.text.toString())
//
//            if (binding.rGroup.checkedRadioButtonId == R.id.rbMt) { // 산 정보 찾기 선택
//                onefragment.arguments = bundle
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.activity_content, onefragment)
//                    .commit()
//            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbTree) { // 식물 정보 찾기 선택
//                twofragment.arguments = bundle
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.activity_content, twofragment)
//                    .commit()
//            }
        }

        // header
        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("mobileapp", "button.setOnClickListener")

            val intent = Intent(this, AuthActivity::class.java)
            if(button.text.equals("로그인")) {
                intent.putExtra("status", "logout")
            }
            else if(button.text.equals("로그아웃")) {
                intent.putExtra("status", "login")
            }
            startActivity(intent)

            binding.drawer.closeDrawers()
        }
    }

    // drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // drawer menu
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_php -> {
                Log.d("mobileapp", "Php 메뉴")
                val intent = Intent(this, PhpActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_board -> {
                Log.d("mobileapp", "앱 평가")
                val intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_addmt -> {
                Log.d("mobileapp", "산 추가 메뉴")
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

            R.id.item_alarm -> {
                Log.d("mobileapp", "나의 등산 월간 리포트")
                val intent = Intent(this, AlarmActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

            R.id.item_youtube -> {
                Log.d("mobileapp", "안전 수칙 익히기")
                val intent = Intent(this, YoutubeActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

            R.id.item_map -> {
                Log.d("mobileapp", "내 위치 확인하기")
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }

        }
        return false
    }

    override fun onStart() {
        super.onStart()

        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth() || MyApplication.email!=null) {
            button.text = "로그아웃"
            tv.text = "${MyApplication.email}님 \n 반가워요!"
        }
        else {
            button.text = "로그인"
            tv.text = "안녕하세요!"
        }
    }

    // sharedPreferences 바로 반영
    override fun onResume() {
        super.onResume()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreferences.getString("color", "#0FA958")
        binding.mtmeTitle.setBackgroundColor(Color.parseColor(color))

        val idstr = sharedPreferences.getString("id", "Mt. Me")
        binding.mtmeTitle.text = idstr

        val size = sharedPreferences.getString("size", "16.0f")
        binding.mtmeTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

        val visible = sharedPreferences.getString("visible", "visible")
        binding.mtmeTitle.visibility = when (visible) {
            "visible" -> View.VISIBLE
            "gone" -> View.GONE
            else -> View.VISIBLE
        }
    }

}