package com.example.mt_me

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mt_me.databinding.ActivityPhpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhp.setOnClickListener {
            val name = binding.etName.text.toString() ?: ""
            val call : Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(name)
            Log.d("mobileApp", "$name")

            call?.enqueue(object : Callback<PhpResponse> {
                override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) {
                    if(response.isSuccessful) {
                        Log.d("mobileApp", "$response")
                        Log.d("mobileApp", "${response.body()}")
                        binding.phpRecyclerView.adapter = PhpAdapter(this@PhpActivity, response.body()?.result!!)
                        binding.phpRecyclerView.layoutManager = LinearLayoutManager(this@PhpActivity)
                        binding.phpRecyclerView.addItemDecoration(DividerItemDecoration(this@PhpActivity, LinearLayoutManager.VERTICAL))
                    }
                }

                override fun onFailure(call: Call<PhpResponse>, t: Throwable) {
                    Log.d("mobileApp", "onFailure")
                }
            })
        }
    }
}