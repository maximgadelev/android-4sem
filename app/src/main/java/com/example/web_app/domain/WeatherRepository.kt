package com.example.web_app.domain

import com.example.web_app.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(name:String): Weather
    suspend fun getWeatherById(id: Int):Weather
    suspend fun getWeatherList(latitude: Double?, longitude: Double?, count:Int):List<Weather>
}