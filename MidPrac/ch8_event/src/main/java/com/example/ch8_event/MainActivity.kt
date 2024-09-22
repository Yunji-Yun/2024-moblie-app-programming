package com.example.ch8_event

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 키 두 번 눌렀음을 확인하기 위한 변수로, 전역 변수로 선언함
    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. 뷰 가져오기
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 위 두 줄과 아래는 동일한 의미
        // setContentView(R.layout.activity_main)

        // 6. 타이머로 센 시간 변수 0으로 초기화
        var prevTime = 0L

        // 2. 버튼 아이디로 가져오기
        // 코틀린에서도 xml 위젯을 변경 가능
        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f

        // 3. 동작 넣기
        binding.startButton.setOnClickListener {
            // it 키워드가 가리키는 것은 스타트 버튼

            // 5. 바인딩의 chronometer 조정
            // 타이머로 카운트 된 시간부터 시작하도록
            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            // 시작점에 현재 시간
            binding.chronometer.start()
            // 그때부터 시작

            // 4. 버튼 비활성화, 활성화 설정
            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
        }
        binding.stopButton.setOnClickListener {
            // 누른 시간부터 스탑을 누른 시간까지를 계산해서 prevTime에 넣기
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime() // start - stop 까지의 시간
            binding.chronometer.stop()
            // chronometer 멈춤

            binding.stopButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.resetButton.isEnabled = false
        }
        binding.resetButton.setOnClickListener {
            // 리셋 누르면 타이머로 카운트한 시간 0으로 초기화
            prevTime = 0L
            binding.chronometer.stop()

            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = true
        }
    }

    // 7. 키보드만 별도로 처리해 주는 함수
    // 상위의 AppCompatActivity 에서 제공 중 -> 상속 받아 사용
    // 오버라이드 하는 법 -> 햄버거바 -> 코드 -> 오버라이드
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) { // 키 코드가 무엇인지 조건문
            // 백키가 들어오면
            KeyEvent.KEYCODE_BACK -> {
                // 지금 누른 시간 - 처음 누른 시간 3초 넘을 때
                if(System.currentTimeMillis() - initTime > 3000) {
                    // Log함수에는 두 가지 스트링이 들어감 -> 태그, 출력할 스트링
                    Log.d("mobileapp", "BACK key가 눌렸어요..종료하려면 한번 더 누르세요..")
                    //  처음 BACK을 누른 시간 업데이트
                    initTime = System.currentTimeMillis()

                    // 다이얼로그 띄우기 -> 토스트 띄우기
                    // 여기에(메인 액티비티), 이러한 텍스트 띄울 것
                    // 토스트를 얼마동안 노출? -> Toast.LENGTH_LONG/LENGTH_SHORT
                    Toast.makeText(this, "BACK Key가 눌렸어요..종료하려면 한번 더 누르세요..", Toast.LENGTH_LONG).show()

                    return true // BACK 키에 대한 처리는 하되, 종료는 하지 않겠다는 의미
                }
            }
            // 볼륨 업 키가 들어오면
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp", "VOLUME_UP key가 눌렸어요..")
            // 볼륨 다운 키가 들어오면
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp", "VOLUME_DOWN key가 눌렸어요..")
        }
        return super.onKeyDown(keyCode, event)
    }
}

// 터치 이벤트
// override fun onTouchEvent(event: MotionEvent?): Boolean {
// MotionEvent.ACTION_UP -> {} DOWN, MOVE