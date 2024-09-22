package com.example.eventapplication

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 위 두 줄과 아래는 동일한 의미
        // setContentView(R.layout.activity_main)

        var prevTime = 0L

        // 코틀린에서도 xml 위젯을 변경 가능
        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f

        // 동작 넣기
        binding.startButton.setOnClickListener {
            // it 키워드가 가리키는 것은 스타트 버튼

            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()

            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
        }
        binding.stopButton.setOnClickListener {
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime() // start - stop 까지의 시간
            binding.chronometer.stop()

            binding.stopButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.resetButton.isEnabled = false
        }
        binding.resetButton.setOnClickListener {
            prevTime = 0L
            binding.chronometer.stop()

            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = true
        }
    }

    // 키보드만 별도로 처리해 주는 함수
    // 상위의 AppCompatActivity 에서 제공 중 -> 상속 받아 사용
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if(System.currentTimeMillis() - initTime > 3000) {  // 3초
                    Log.d("mobileapp", "BACK key가 눌렸어요..종료하려면 한번 더 누르세요..")
                    initTime = System.currentTimeMillis() //  처음 BACK을 누른 시간이 저장

                    Toast.makeText(this, "BACK Key가 눌렸어요..종료하려면 한번 더 누르세요..", Toast.LENGTH_LONG).show()

                    return true // BACK 키에 대한 처리는 하되, 종료는 하지 않겠다는 의미
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp", "VOLUME_UP key가 눌렸어요..")
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp", "VOLUME_DOWN key가 눌렸어요..")
        }
        return super.onKeyDown(keyCode, event)
    }
}