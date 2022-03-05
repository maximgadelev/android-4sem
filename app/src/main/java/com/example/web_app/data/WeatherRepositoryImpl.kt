package com.example.web_app.data

import androidx.viewbinding.BuildConfig
import com.example.web_app.data.api.Api
import com.example.web_app.data.api.mapper.WeatherMapper
import com.example.web_app.data.response.WeatherList
import com.example.web_app.data.response.WeatherResponse
import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.entity.Weather
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val QUERY_UNITS = "units"
private const val UNITS = "metric"
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "9e8331105a420290e0619d9769c5dc65"
private const val QUERY_API_KEY = "appid"

class
WeatherRepositoryImpl(
    private val api:Api,
   private val mapper: WeatherMapper
):WeatherRepository {



    override suspend fun getWeatherByCity(city: String): Weather {
        return mapper.mapToWeather(api.getWeatherByCity(city))
    }
    override suspend fun getWeatherById(id: Int): Weather {
        return mapper.mapToWeather(api.getWeatherById(id))
    }
    override suspend fun getWeatherList(latitude: Double?, longitude: Double?, count:Int): List<Weather> {
        return mapper.mapToWeatherList(api.getWeatherList(latitude,longitude,count))
    }
}