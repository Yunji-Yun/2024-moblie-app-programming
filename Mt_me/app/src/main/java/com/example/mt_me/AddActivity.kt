package com.example.mt_me

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mt_me.databinding.ActivityAddBinding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    var datas : MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 추가한 산 저장할 datas
        datas = mutableListOf<String>()
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from mt_tb", null)
        while (cursor.moveToNext()) {
            datas?.add(cursor.getString(1))
        }
        db.close()

        // 파일 읽기
        val file = File(filesDir, "mt.txt")
        val readstream: BufferedReader = file.reader().buffered()
        binding.lastsaved.text = "마지막 저장시간 : " + readstream.readLine()

        // adapter
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        val requestLauncher: ActivityResultLauncher<Intent>
                = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            it.data!!.getStringExtra("result")?.let{
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()

                    // 파일 읽기
                    val file = File(filesDir, "mt.txt")
                    val readstream: BufferedReader = file.reader().buffered()
                    binding.lastsaved.text = "마지막 저장시간 : " + readstream.readLine()
                }
            }
        }

        // add btn 눌러 산 추가
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddmtActivity::class.java)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
    }
}