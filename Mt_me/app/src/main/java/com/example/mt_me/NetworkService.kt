package com.example.mt_me

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("PHP_connection.php")
    fun getPhpList(
        @Query("MtName") mtName:String
    ) : Call<PhpResponse>

    @GET("getforeststoryservice")
    fun getXmlList (
        @Query("mntnNm")  mntnNm:String,
        @Query("serviceKey")  serviceKey:String
    ) : Call<XmlResponse>

    @GET("fStoryOpenAPI")
    fun getPlantList (
        @Query("searchWrd")  searchWrd:String,
        @Query("ServiceKey")  ServiceKey:String,
        @Query("pageNo")  pageNo:Int,
        @Query("numOfRows")  numOfRows:Int
    ) : Call<PlantResponse>
}