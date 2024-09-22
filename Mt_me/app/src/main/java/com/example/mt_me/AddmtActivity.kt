package com.example.mt_me

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mt_me.databinding.ActivityAddmtBinding
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddmtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddmtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setOnClickListener {
            val edt_str = binding.addEditView.text.toString()
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(Activity.RESULT_OK, intent)

            // db에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into mt_tb (mt) values (?)", arrayOf<String>(edt_str))
            db.close()

            // 파일 저장하기
            val dataFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss") // 년 월 일 시 분 초
            val file = File(filesDir, "mt.txt")
            val writestream: OutputStreamWriter = file.writer()
            writestream.write(dataFormat.format(System.currentTimeMillis()))
            writestream.flush()

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