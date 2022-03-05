package com.example.web_app.data.api.mapper

import com.example.web_app.data.response.*
import com.example.web_app.domain.entity.Weather

class WeatherMapper {
    fun mapToWeather(response: WeatherResponse): Weather = Weather(
        id=response.id,
        name=response.name,
        temp = response.main.temp,
        tempMax = response.main.tempMax,
        tempMin = response.main.tempMin,
        humidity = response.main.humidity,
        pressure = response.main.pressure,
        sunrise = response.sys.sunrise,
        sunset = response.sys.sunset,
        speed = response.wind.speed,
        deg = response.wind.deg
    )
    fun mapToWeatherList(weatherList: WeatherList):List<Weather>{
        val newList= arrayListOf<Weather>()
        for(i in weatherList.list){
            newList.add(mapToWeatherFromListResponse(i))
        }
        return newList
    }
    fun mapToWeatherFromListResponse(response: WeatherList.WeatherResponse):Weather=Weather(
        id=response.id,
        name=response.name,
        temp = response.main.temp,
        tempMax = response.main.tempMax,
        tempMin = response.main.tempMin,
        humidity = response.main.humidity,
        pressure = response.main.pressure,
        //костыль,тк в везерлист.везер респонсе нет санрайса с сансетом,но на отображение в rv не влияют,хотя можно сделать было отдельную сущность,но слишком глупо,наверное
        sunrise = 123,
        sunset = 123,
        speed = response.wind.speed,
        deg = response.wind.deg)
}