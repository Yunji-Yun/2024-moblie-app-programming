package com.example.ch18_image

import com.example.ch18_image.XmlResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3
// https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3?serviceKey=n%2F7fVcllIsn%2BSbZE9RQagnKysnAGx17cS7lGCITkUNvvd4jGID0W%2BFXrz1AGrjk3MZKTl5lzmcM%2BRxNpx2j56g%3D%3D&

interface NetworkService {
    // http://localhost/PHP_connection.php?age=22
    @GET("PHP_connection.php")
    fun getPhpList(
        @Query("age") age:String
    ) : Call<PhpResponse>

    @GET("getCertImgListServiceV3")
    fun getXmlList(
        @Query("prdlstNm") name:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") apiKey:String
    ) : Call<XmlResponse>
}