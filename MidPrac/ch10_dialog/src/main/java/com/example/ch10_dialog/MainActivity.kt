package com.example.ch10_dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.ch10_dialog.databinding.ActivityMainBinding
import com.example.ch10_dialog.databinding.DialogCustomBinding
import com.google.android.material.navigation.NavigationView

// 5를 위해 NavigationView 상속
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    // 4. 드로어 메뉴를 위한 토글 설정하기
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 4-1. 토글 초기화
        // ActionBarDrawerToggle(호출, 연결할 xml, 오픈했을 때 스트링, 클로즈했을 떄 스트링(스트링에 정의해놨음))
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawaer_opened, R.string.drawer_closed)
        // 4-2. 액션바에 토글이 보이도록 표시 -> 아래로
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 5. 드로어 메뉴에 리스너 추가 -> 위에 상속 추가
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        // 1. 각 버튼에 다이어로그 연결
        binding.btnDate.setOnClickListener {
            // 1-1. 날짜를 선택하는 다이어로그
            // DatePickerDialog(표시할 곳(메인 액티비티), 날짜 선택 리스너, 초기날짜(월은 0부터 시작) 설정).show()
            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{ // 오브젝트로 이름없는 함수 선언
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    // 토스트 -> 메인 액티비티가 아닌 데이트피커 다이어로그에서 호출하므로 applicationContext 사용
                    Toast.makeText(applicationContext, "$year 년 ${month+1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()
                    binding.btnDate.text = "$year 년 ${month+1} 월 $dayOfMonth 일"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.parseColor("#00ffff"))
                }
            }, 2024, 3, 3).show()
        }

        binding.btnTime.setOnClickListener {
            // 1-2. 시간을 선택하는 다이어로그
            // TimePickerDialog(표시할 곳(메인 액티비티), 시간 선택 리스너, 초기 시간, 24시간 기준 여부).show()
            TimePickerDialog(this, object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(applicationContext, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()
                    binding.btnTime.text = "$hourOfDay 시 $minute 분"
                    binding.btnTime.textSize = 24f
                    binding.btnTime.setTextColor(Color.parseColor("#ffff00"))
                }
            }, 15, 29, true).show()
        }


        // 2. AlertDialog 확인창
        // setIcon, setTitle, setMessage
        // setPositiveButton, setNegativeButton, setNeutralButton

        // 2-1. 단순 확인창
        // 예 혹은 아니오 눌렀을 때 나타나는 이벤트
        val eventHandler = object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) { // 예를 누르면
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                } else if (which == DialogInterface.BUTTON_NEGATIVE) { // 아니오를 누르면
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlert.setOnClickListener {
            // AlertDialog.Builder(this).run { 내용 }
            AlertDialog.Builder(this).run {
                setTitle("알림창 - 모앱")
                // 아이콘 설정
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("예", eventHandler) // 이벤트 연결
                setNegativeButton("아니오", eventHandler)
                // setNeutralButton("상세정보", null)
                show()
            }
        }

        // 2-2. 목록 중 하나 선택 (setItems)
        val items = arrayOf<String>("빨강", "노랑", "파랑", "추록")
        binding.btnAlertItem.setOnClickListener {
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - 아이템")
                setIcon(android.R.drawable.ic_dialog_alert)
                setItems(items, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) { // which -> 몇 번 선택
                        Log.d("mobileapp", "${items[which]} 선택")
                        binding.btnAlertItem.text = "${items[which]} 선택"
                    }
                })
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                // setNeutralButton("상세정보", null)
                show()
            }
        }

        // 2-3. 목록 중 하나 선택 -> 최종 선택 (setSingleChoiceItems)
        var selected = 0
        // 다른 곳에서도 위치 값을 쓸 수 있도록 변수 선언
        val eventHandler2 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    // 최종 선택 버튼에 표시
                    binding.btnAlertSingle.text = "${items[selected]} 선택"
                }
                else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlertSingle.setOnClickListener {
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - Single")
                setIcon(android.R.drawable.ic_dialog_alert)
                // 1번 초기 선택
                setSingleChoiceItems(items, 1, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]} 선택")
                        selected = which
                    }
                })
                setPositiveButton("예", eventHandler2)
                setNegativeButton("아니오", eventHandler2)
                // setNeutralButton("상세정보", null)
                show()
            }
        }

        // 2-4. 목록 중 여러 개 선택 (setMultiChoiceItems)
        binding.btnAlertMulti.setOnClickListener {
            AlertDialog.Builder(this).run(){
                setTitle("알림창 - 다수 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                // 초기 선택
                setMultiChoiceItems(items, booleanArrayOf(false, true, true, false), object:
                    DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${items[which]} ${if(isChecked) "선택" else "해제"}")
                    }
                })

                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                show()
            }
        }

        // 2-5. xml 파일에서 선택지 가져오기 (setMultiChoiceItems)
        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)
        val eventHandler3 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    if(dialogBinding.rbtn1.isChecked){ // 아이디 rbtn1가 체크됐다면
                        binding.btnAlertCustom.text = dialogBinding.rbtn1.text.toString() // rbtn1의 문자를 스트링 타입으로 가져와라
                    }
                    else if(dialogBinding.rbtn2.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn2.text.toString()
                    }
                    else if(dialogBinding.rbtn3.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn3.text.toString()
                    }
                    else if(dialogBinding.rbtn4.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn4.text.toString()
                    }
                }
                else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        val customDlg = AlertDialog.Builder(this).run(){
            setTitle("알림창 - 사용자 화면")
            setIcon(android.R.drawable.ic_dialog_alert)
            // dialog_custom.xml 파일 선택지 가져오기
            setView(dialogBinding.root)

            setPositiveButton("예", eventHandler3)
            setNegativeButton("아니오", eventHandler3)
            create()
        }
        binding.btnAlertCustom.setOnClickListener {
            customDlg.show()
        }
    } // OnCreate()

    // 5-1. 상속 받았으니 오버라이드
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_info -> {
//                Log.d("mobileapp", "Option Menu : 메뉴 1")
//                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }

            R.id.item_map -> {
//                Log.d("mobileapp", "Option Menu : 메뉴 2")

                // 지도 보기
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))

                // 길찾기
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/서울역/수유역/"))
                startActivity(intent)
                true
            }

            R.id.item_gallery -> {
//                Log.d("mobileapp", "Option Menu : 메뉴 3")

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
                startActivity(intent)
                true
            }

            R.id.item_call -> {
//                Log.d("mobileapp", "Option Menu : 메뉴 4")

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-911"))
                startActivity(intent)
                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kmlee@duksung.ac.kr"))
                startActivity(intent)
                true
            }
        }
        return false
    }

    // 3. 메뉴 더하기 (onCreateOptionsMenu, onOptionsItemSelected)
    // Option Menu

    // 3-1. 메뉴를 연결해주는 역할
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 리소스 > 메뉴 > menu_navigation.xml
        menuInflater.inflate(R.menu.menu_navigation, menu)

        // 4. 액션 뷰를 이용하는 서치 메뉴
        // SearchView -> alt + 엔터 -> androidx.appcompat.widget 불러오기
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 서치 뷰가 부르므로 디스가 아닌 아래처럼 적음
                Toast.makeText(applicationContext, "$query 검색합니다.", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    // 3-2. 메뉴 눌렀을 때 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 5-3. 이벤트에 토글도 추가
        if(toggle.onOptionsItemSelected(item)){
            return true // 그냥 본래의 기능 실행
        }

        when(item.itemId){
            R.id.item1 -> { // 1번 메뉴 선택
                Log.d("mobileapp", "Option Menu : 메뉴 1")
                // binding 쓰려면 전역 변수 선언 필수 (위에 정의되어 있음)
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item2 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 2")
                true
            }
            R.id.item3 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 3")
                true
            }
            R.id.item4 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 4")
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}