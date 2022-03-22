package com.example.web_app.di

import androidx.viewbinding.BuildConfig
import com.example.web_app.data.WeatherRepositoryImpl
import com.example.web_app.data.api.Api
import com.example.web_app.data.api.mapper.WeatherMapper
import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.usecase.GetWeatherByCityUseCase
import com.example.web_app.domain.usecase.GetWeatherByIdUseCase
import com.example.web_app.domain.usecase.GetWeatherListUseCase
import kotlinx.coroutines.Dispatchers
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

object DIContainer {
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(
                QUERY_API_KEY,
                API_KEY
            )
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }
    private val unitsInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL: HttpUrl = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, UNITS)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }
    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

  val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
    val weatherRepository:WeatherRepository=WeatherRepositoryImpl(
        api = api,
        mapper = WeatherMapper()
    )
    val getWeatherListUseCase:GetWeatherListUseCase= GetWeatherListUseCase(
        weatherRepository = weatherRepository
    )
    val getWeatherByCityUseCase:GetWeatherByCityUseCase= GetWeatherByCityUseCase(
        weatherRepository= weatherRepository
    )
    val getWeatherByIdUseCase:GetWeatherByIdUseCase= GetWeatherByIdUseCase(
        weatherRepository= weatherRepository
    )
}