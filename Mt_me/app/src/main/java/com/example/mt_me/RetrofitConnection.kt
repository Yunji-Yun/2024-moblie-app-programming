package com.example.mt_me

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//http://api.forest.go.kr/openapi/service/trailInfoService/getforeststoryservice?mntnNm=%EC%84%A4%EC%95%85%EC%82%B0&serviceKey=n%2F7fVcllIsn%2BSbZE9RQagnKysnAGx17cS7lGCITkUNvvd4jGID0W%2BFXrz1AGrjk3MZKTl5lzmcM%2BRxNpx2j56g%3D%3D

class RetrofitConnection {
    companion object {
        private const val BASE_URL = "http://api.forest.go.kr/openapi/service/trailInfoService/"
        private const val BASE_URL2 = "http://api.forest.go.kr/openapi/service/cultureInfoService/"

        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        val plantNetServ : NetworkService
        val parser2 = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val plantRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(TikXmlConverterFactory.create(parser2))
                .build()

        private const val BASE_URL_Php = "http://192.168.45.62/"
        val phpNetworkService : NetworkService
        val phpRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_Php)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init {
            xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
            plantNetServ = plantRetrofit.create(NetworkService::class.java)
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
        }
    }
}