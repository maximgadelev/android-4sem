package com.example.web_app.domain.usecase

import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByIdUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(id:Int): Weather {
        return withContext(Dispatchers.Main){
            weatherRepository.getWeatherById(id)
        }
    }
}