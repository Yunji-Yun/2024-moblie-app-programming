package com.example.mt_me

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mt_me.databinding.ActivityAlarmBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class AlarmActivity : AppCompatActivity() {
    var juneClimbs = 2  // 6월 등산 횟수를 저장할 변수
    lateinit var binding:ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 알림 notification
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() ) {
            if (it.all { permission -> permission.value == true }) {
                noti()
            }
            else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.notificationButton.setOnClickListener {
            // Animation
            var anim = AnimationUtils.loadAnimation(this, R.anim.anim)
            binding.notificationButton.startAnimation(anim)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                }
                else {
                    permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                }
            }
            else {
                noti()
            }
            juneClimbs += 1
        } // binding.notificationButton

        // graph
        val line_values: ArrayList<Entry> = ArrayList()
        // 5월까지의 등산 횟수
        for (i in 0 until 5) {
            var v = Math.random()
            line_values.add(Entry(i.toFloat(), v.toFloat()))
        }
        // 6월 등산 횟수
        line_values.add(Entry(5.toFloat(), juneClimbs.toFloat()))
        val linedataset = LineDataSet(line_values, "LineDataSet")
        val gr1 = Color.parseColor("#06331C")
        val gr2 = Color.parseColor("#0FA958")
        linedataset.color = gr2
        linedataset.lineWidth = 3f
        linedataset.setCircleColor(gr1)
        val linedata = LineData(linedataset)
        binding.lineChart.data = linedata
    }

    fun noti(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.drawable.alarm)
            setWhen(System.currentTimeMillis())
            setContentTitle("오늘의 등산 인증 완료!")
            setContentText("Mt. Me에 한 걸음 더 가까워지셨네요. 다음 등산도 파이팅!")
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.climbing))
        }

        manager.notify(11, builder.build())
    }


}