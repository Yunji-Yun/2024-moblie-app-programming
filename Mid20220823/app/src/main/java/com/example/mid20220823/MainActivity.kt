package com.example.mid20220823

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mid20220823.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    // 4. 드로어 메뉴를 위한 토글 설정하기
    lateinit var toggle : ActionBarDrawerToggle


    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment())
        }
        override fun getItemCount(): Int {
            return fragments.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        binding.viewpager.adapter = MyFragmentPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewpager) {
                tab, position ->
            tab.text = "${position+2}번"
        }.attach()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.65198, 127.0162"))
                startActivity(intent)
                true
            }

            R.id.item_mapmode -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/덕성여자대학교/수유역/"))
                startActivity(intent)
                true
            }

            R.id.item_call -> {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:911"))
                startActivity(intent)
                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kmlee@ds.ac.kr"))
                startActivity(intent)
                true
            }
        }
        binding.drawer.closeDrawers()
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 5-3. 이벤트에 토글도 추가
        if(toggle.onOptionsItemSelected(item)){
            return true // 그냥 본래의 기능 실행
        }
        return super.onOptionsItemSelected(item)
    }
}