package com.example.web_app.data

import com.example.web_app.data.api.Api
import com.example.web_app.data.api.mapper.WeatherMapper
import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.entity.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val mapper: WeatherMapper
) : WeatherRepository {


    override suspend fun getWeatherByCity(city: String): Weather {
        return mapper.mapToWeather(api.getWeatherByCity(city))
    }

    override suspend fun getWeatherById(id: Int): Weather {
        return mapper.mapToWeather(api.getWeatherById(id))
    }

    override suspend fun getWeatherList(
        latitude: Double?,
        longitude: Double?,
        count: Int
    ): List<Weather> {
        return mapper.mapToWeatherList(api.getWeatherList(latitude, longitude, count))
    }
}