package com.example.web_app.domain.usecase

import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


    class GetWeatherListUseCase (
        private val weatherRepository: WeatherRepository
    ){
        suspend operator fun invoke(latitude: Double?, longitude: Double?, count:Int): List<Weather> {
            return withContext(Dispatchers.Main){
                weatherRepository.getWeatherList(latitude,longitude,count)
            }
        }
    }
