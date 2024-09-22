package com.example.midprac

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }
    /*
    03. 코틀린 시작하기
    03-2 변수와 함수
    - 변수 선언
    val (var) 변수명: 타입 = 값

    fun main() {
        println("Hello, world!!!")

        var data1 = 10 // 변경 가능 variable
        val data2 = 10 // 변경 불가능 value

        data1 = 20
        data2 = 10 // 오류

    }

    - 타입 선언
    → Int, Short, Long, Double, Float, Byte, Boolean, Char, String

    ```kotlin
    // 전역 변수는 초기값 필수 (초기화 필수)
    var data5 : Int = 40
    var data5 : Int // 오류

    // 초기화 나중에 하는 경우 (오직 var, String만 가능)
    lateinit var data6 : String

    fun main() {
        println("Hello, world!!!")

        // 데이터 타입 선언 시 첫 글자는 대문자
        var data3:Int = 20

        // 나눠서 선언 가능
        var data4:Double
        data4 = 12.34

        /* 실험 -> 오류
        var data8 = 8
        data8 = "string"
        println(data8)
        */

        // $는 해당 변수의 값 출력
        println(data1)
        println("this is data1 : $data1")

        // 변수 타입 자유롭게
        var data6: Any
        data6= 10
        println(data6)
        data6= "app"
        println(data6)

        var data7 // null이 아님
        // 코틀린은 null을 허용하지 않으므로 null point exception이 없음
        data7 = null // 오류

        var data8:Int? // Int가 들어갈 수 있지만 ?로 null 넣을 수 있게 됨
        data8 = null // 가능

    }
    ```


    - 함수 선언

    ```kotlin
    // fun 함수명(매개변수:타입):리턴타입선언
    fun sum10(data1:Int):Int{
        return data1 + 10
    }

    // 초기값 선언
    fun sum(data1:Int, data2:Int=10):Int{
        return data1 + data2
    }

    fun main() {
        val kotlin = "🙂"
        println(kotlin)
        println(sum10(5))

        println(sum(3,5)) // 매개변수 전달
        println(sum(7))
    }
    ```


    - 배열 : Array
    → Int, Short, Long, Double, Float, Boolean, Byte, Char

    ```kotlin
    fun main() {
        // var 배열:Array<타입> = Array(배열 길이, {원소})
        var arr1:Array<Int> = Array(3, {0}) // 정수 배열 타입 선언

        // 배열에 원소 삽입
        arr1[0] = 20 // arr1.set(0, 20)
        arr1.set(2, 30) // arr1[2] = 30

        // var 배열 = arrayOf<타입> = Array(원소1, ...)
        var arr2 = arrayOf<Int>(10,20,30)

        // Int 배열 자체 선언
        var arr3: IntArray = IntArray(3,{0})
        // var arr3:Array<Int> = Array(3, {0})
        // LongArray, DoubleArray, StringArray 등 다 됨

        var arr4 = intArrayOf(10,20,30)
        // var arr4:Array<Int> = Array(3, {10, 20, 30})
        // LongArrayOf(?), doubleArrayOf, stringArrayOf

        println(arr1[0])
        println(arr2.get(2))

        println("배열 arr3의 2번째 원소 : ${arr3}[1]")
        // $가 arr3에 붙어있고 실제 원소에 안붙어서 오류
        println("배열 arr3의 2번째 원소 : ${arr3[1]}")

    }
    ```


    - 배열 : List
    → Int, Short, Long, Double, Float, Boolean, Byte, Char

    ```kotlin
    fun main() {
        var list1:List<Int> = List(3,{0})
        var list2 = listOf<Int>(10,20,30)
        var list3 : IntList = IntList(3,{0}) // 오류
        // 대문자 :List는 크기 변경 불가
        // 크기 변경이 가능한 리스트의 경우 데이터 추가 가능
        // 배열은 처음 세팅한 메모리 공간의 변경이 불가능
        var list3 = mutableListOf<Int>(10, 20, 30)
        // mutableList은 크기 변경 가능
        list3.add(3, 40) // list3 = [10, 20, 30, 40]
        list3.set(2, 15) // list3 = [10, 20, 15, 40]
        println("mutableList : ${list3[3]}")

        ```

    }
    ```

    * Array와 List의 차이 → Array는 연속된 메모리 공간, List 링크로 연결

    → 인수가 연속으로 붙어있을 필요 없고 순서만 연결
    → 정적으로 연속적인 메모리 공간을 불러오는 array


    * 추가적으로 교재에는 Map 함수가 있음

    ### 03-3 조건문과 반복문

    - 조건문

    ```kotlin
    fun main() {
        println("조건문")

        var data1 = 10

        if (data1 > 0) {
            println("positive")
        }
        else if (data1 == 0) {
            println("0")
        }
        else {
            println("0 or negative")
        }
    }
    ```


    - 조건문을 표현식으로 사용 가능

    ```kotlin
    fun main() {
        var data1 = 10
        var data2:Int

        if (data1 > 0) {
            data2 = 1
        }
        else if (data1 == 0) {
            data2 = 0
        }
        else {
            data2 = -1
        }

        data2 = if (data1 > 0) {
            1
        }
        else if (data1 == 0) {
            0
        }
        else {
            -1
        }

    }
    ```


    - switch case → when

    ```kotlin
    when(data1) {
        10 -> println("10")
        20 -> println("20")
        30,40 -> println("30, 40") // 값 두개 가능
        in 1..10 -> println("1..10") // 범위
        is Int -> println("Int") // 타입 체크
        else -> println("else") // 디폴트
    }

    var data5 = when(data1) {
        10 -> "10"
        20 -> "20"
        30,40 -> "30, 40"
        in 1..10 -> "1..10"
        is Int -> "Int"
        else -> "else"
    }
    println(data5)

    ```


    - 반복문

    ```kotlin
    fun main() {
        println("반복문")

        var sum1 = 0
        for(i in 0..9 step 1) { // 0에서 9까지 1씩 증가(1인 경우 생략 가능)
            sum1 += i // sum = sum + i
        }
        println(sum1)

        var sum2 = 0
        for(i in 9 downTo 0 step 2) { // 9에서 0까지 2씩 감소하며 더하는 경우 (생략시 1씩 감소)
            sum2 += i // sum = sum + i
        }
        println(sum2)

        // for 배열
        var dataArray = arrayOf<Int>(10, 20, 30)
        for(i in dataArray.indices){ // dataArray의 데이터 개수만큼 반복
            println(dataArray[i])
        }
    }
    ```


    ## 04. 코틀린 객체지향 프로그래밍

    ### 04-1 클래스와 생성자

    - 클래스

    ```kotlin
    // 매개 변수 -> var, val 붙이면 멤버 변수
    // class User1(var name:String) {
    class User1(name:String) {
        var name = "yun" // 멤버 변수

        constructor(name:String) { // 생성자
            println("constructor")
            this.name = name
        }

        init { // 생성자 대체, 자동 실행
            this.name = name
            println("init")
        }

        fun someFun(){ // 멤버 함수
            println(name)
        }

        class someClass {} // 멤버 클래스
    }

    fun main() {
        println("class")

        val user1 = User1("kim")
        user1.someFun()
    }
    ```


    ### 04-2 클래스를 재사용하는 상속

    - 상속

    ```kotlin
    class specialUser(name:String):User1(name){ // User1 상속
        override var name = "choi"
        override fun someFun() {
            println("specialUser..${[this.name](http://this.name/)}")
                [this.name](http://this.name/) = name
            println("specialUser..${[this.name](http://this.name/)}")
            }
                }

                // 오버라이드를 허용하려면 open 키워드 사용
                // open class User1(name:String) {
                // open var name = "yun"
                // open fun someFun(){}

                fun main() {
                    println("class")

                    val user1 = User1("kim")
                    user1.someFun()

                    val suser = specialUser("park")
                    suser.someFun()
                }
                ```


                ### 04-3 코틀린의 클래스 종류

                        - 데이터 클래스

                        ```kotlin
                        data class dataClass(val name:String, val age:Int, val mail:String)
                // class dataClass() {} 형태가 아닌 데이터 저장만

                fun main() {
                    val data1 = dataClass("mobile", 21, "ds@duksung.ac.kr")
                    println(data1.toString())
                }
                ```


                - 오브젝트 클래스

                        ```kotlin
                fun main() {
                    val obj = object {
                        var data2 = 0
                        fun some() {
                            prtinln(data)
                        }
                    }
                    obj.data2 = 20
                    obj.some()
                }
                ```


                - 컴패니언 클래스

                        ```kotlin
                class User3 {
                    companion object { // 객체 생성 하지 않아도 접근
                    var data3 = 20
                    fun someFun3() {
                        println(data3)
                    }
                }
                }

                fun main() {
                    // var user3 = User3() 처럼 객체를 생성하지 않아도
                    User3.data3 = 30 // 클래스명. 으로 접근 가능
                    User3.someFun3()
                }
                ```


                ## 05. 코틀린의 유용한 기법

                ### 05-1 람다 함수와 고차함수

                        - 람다 함수

                        ```kotlin
                fun sum(n1:Int, n2:Int):Int {return n1+n2}

                fun main() {
                    println("null")

                    println(sum(5,6))

                    // 익명 함수: 멤버 변수 -> 리턴값
                    var sum1:(Int,Int)->Int = {n1:Int, n2:Int -> n1+n2}
                    println(sum1(7,6))
                }
                ```


                ### 05-2 널 안전성
                - 널 안전성 연산자
                fun main() {
                        // 널 허용 연산자 ?
                        var data1:String? = null

                        data1 = "mobile"
                        // !!(예외 발생 연산자)는 data1이 절대 null 이면 안된다는 뜻
                        println(data1!!.length)
                }

     */
}