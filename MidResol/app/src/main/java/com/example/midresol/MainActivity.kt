package com.example.midresol

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.midresol.databinding.ActivityMainBinding
import com.example.midresol.databinding.DialogCustomBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
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

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawaer_opened,
            R.string.drawer_closed
        )
        // 4-2. 액션바에 토글이 보이도록 표시 -> 아래로
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
        binding.mainDrawerView.setNavigationItemSelectedListener(this)
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "TAB ${position + 1}"
        }.attach()


    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_info -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }

            R.id.item_map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.6513783, 127.0163402"))
                startActivity(intent)
                true
            }

            R.id.item_call -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:911"))
                startActivity(intent)
                true
            }
        }
        return false
    }

    val dialogBinding = DialogCustomBinding.inflate(layoutInflater)
    val eventHandler3 = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.d("mobileapp", "BUTTON_POSITIVE")
            }
        }
    }
    val customDlg = AlertDialog.Builder(applicationContext).run() {
        setTitle("검색어 입력 확인")
        setView(dialogBinding.root)

        setPositiveButton("닫기", eventHandler3)
        create()
    }

    // 3-1. 메뉴를 연결해주는 역할
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 리소스 > 메뉴 > menu_navigation.xml
        menuInflater.inflate(R.menu.menu_navigation, menu)

        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                customDlg.show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true // 그냥 본래의 기능 실행
        }

        when(item.itemId){
            R.id.menu_login -> {
                Toast.makeText(applicationContext, "개발 중 입니다.", Toast.LENGTH_LONG).show()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}