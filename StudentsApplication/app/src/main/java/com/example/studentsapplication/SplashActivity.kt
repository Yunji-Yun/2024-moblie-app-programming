package com.example.studentsapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_splash)

        val backgroundExe : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExe:Executor = ContextCompat.getMainExecutor(this)
        backgroundExe.schedule({
            mainExe.execute({   // 쓰레드 실행
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
            },
            3, TimeUnit.SECONDS)
    }
}