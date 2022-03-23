package com.example.web_app.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.web_app.domain.entity.Weather
import com.example.web_app.domain.usecase.GetWeatherByCityUseCase
import com.example.web_app.domain.usecase.GetWeatherListUseCase
import kotlinx.coroutines.launch

class SearchFragmentViewModel(
    private val getWeatherListUseCase: GetWeatherListUseCase,
    private val getWeatherByCityUseCase:GetWeatherByCityUseCase
):ViewModel() {
    private val _weatherList:MutableLiveData<Result<List<Weather>>> = MutableLiveData()
    val weatherList:LiveData<Result<List<Weather>>> = _weatherList

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather
    fun getWeatherList(changingLatitude:Double?,changingLongitude:Double?){
        viewModelScope.launch {
            try {
                val weatherList = getWeatherListUseCase(changingLatitude, changingLongitude, 10)
                _weatherList.value = Result.success(weatherList)
            }catch (ex: Exception){
                _weather.value = Result.failure(ex)
            }
        }
    }
    fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByCityUseCase(city)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }
}