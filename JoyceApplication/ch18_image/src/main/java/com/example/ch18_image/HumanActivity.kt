package com.example.ch18_image

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch18_image.databinding.ActivityHumanBinding

class HumanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name")
        binding.tvAge.text = intent.getStringExtra("age")
        binding.tvAddr.text = intent.getStringExtra("addr")

    }
}