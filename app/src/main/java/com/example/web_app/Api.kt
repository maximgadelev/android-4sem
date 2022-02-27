package com.example.web_app

import com.example.web_app.response.WeatherList
import com.example.web_app.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather?units=metric&lang=EN")
    suspend fun getWeatherByCity(@Query("q") city: String): WeatherResponse
@GET("weather?units=metric&lang=EN")
    suspend fun getWeatherById(@Query("id") id:Int):WeatherResponse
    @GET("find?units=metric&lang=EN")
    suspend fun getWeatherList(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("cnt") count:Int
    ):WeatherList
}