package com.example.mt_me

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mt_me.databinding.ActivityBoardBinding
import com.google.firebase.firestore.Query

class BoardActivity : AppCompatActivity() {
    lateinit var binding : ActivityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, PlusActivity::class.java))
            }
            else{
                Toast.makeText(this, "로그인 후에 평가 남겨주세요!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(MyApplication.checkAuth()){
            MyApplication.db.collection("comments")
                .orderBy("date_time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {result ->
                    val itemList = mutableListOf<ItemData>()
                    for( document in result){
                        val item = document.toObject(ItemData::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = BoardAdapter(this, itemList)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_LONG).show()
                }
        }
    }
}