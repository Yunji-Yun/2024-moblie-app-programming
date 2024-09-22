package com.example.graphapplication

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.graphapplication.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val line_values: ArrayList<Entry> = ArrayList()
        // line_values.add(Entry(x, y))
        for (i in 0 until 10) {
            var v = Math.random() * 10
            line_values.add(Entry(i.toFloat(), v.toFloat()))
        }
        val linedataset = LineDataSet(line_values, "LineDataSet")
        linedataset.color = Color.GREEN
        linedataset.lineWidth = 3f
        linedataset.setCircleColor(Color.MAGENTA)
        val linedata = LineData(linedataset)
        binding.lineChart.data = linedata

    }
}