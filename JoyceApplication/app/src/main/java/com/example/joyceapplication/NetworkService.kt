import com.example.joyceapplication.JsonResponse
import com.example.joyceapplication.XmlResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 기본 주소 뒤에 선언
// https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&nu%20mOfRows=100&returnType=json&serviceKey=n%2F7fVcllIsn%2BSbZE9RQagnKysnAGx17cS7lGCITkUNvvd4jGID0W%2BFXrz1AGrjk3MZKTl5lzmcM%2BRxNpx2j56g%3D%3D
interface NetworkService {
    @GET("getUlfptcaAlarmInfo")
    fun getJsonList (
        @Query("year")  year:Int,
        @Query("pageNo")  pageNo:Int,
        @Query("numOfRows")  numOfRows:Int,
        @Query("returnType")  returnType:String,
        @Query("serviceKey")  serviceKey:String
    ) : Call<JsonResponse>

    @GET("getUlfptcaAlarmInfo")
    fun getXmlList (
        @Query("year")  year:Int,
        @Query("pageNo")  pageNo:Int,
        @Query("numOfRows")  numOfRows:Int,
        @Query("returnType")  returnType:String,
        @Query("serviceKey")  serviceKey:String
    ) : Call<XmlResponse>
}