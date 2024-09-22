package com.example.mt_me

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mt_me.databinding.ActivitySplashBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animation
        var treeAnimation : AnimationDrawable
        val animImage = binding.treeAnim.apply {
            setBackgroundResource(R.drawable.tree)
            treeAnimation = background as AnimationDrawable
        }
        treeAnimation.start()

        val backgroundExe : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExe: Executor = ContextCompat.getMainExecutor(this)
        backgroundExe.schedule({
            mainExe.execute({
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
        },
            5, TimeUnit.SECONDS)
    }
}