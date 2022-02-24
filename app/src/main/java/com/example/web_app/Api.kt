package com.example.web_app

import com.example.web_app.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather?units=metric&lang=FR")
    suspend fun getWeatherByCity(@Query("q") city: String): WeatherResponse
@GET("weather?units=metric&lang=FR")
    suspend fun getWeatherById(@Query("id") id:Int):WeatherResponse
}