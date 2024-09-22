package com.example.joyceapplication

import NetworkService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&nu%20mOfRows=100&returnType=json&serviceKey=n%2F7fVcllIsn%2BSbZE9RQagnKysnAGx17cS7lGCITkUNvvd4jGID0W%2BFXrz1AGrjk3MZKTl5lzmcM%2BRxNpx2j56g%3D%3D

class RetrofitConnection {
    companion object {
        private const val BASE_URL = "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/"

        val jsonNetServ : NetworkService
        val jsonRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        init {
            jsonNetServ = jsonRetrofit.create(NetworkService::class.java)
            xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
        }
    }
}