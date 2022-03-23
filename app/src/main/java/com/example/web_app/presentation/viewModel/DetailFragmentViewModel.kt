package com.example.web_app.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.web_app.domain.entity.Weather
import com.example.web_app.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch

class DetailFragmentViewModel(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
):ViewModel() {
    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather
    fun getWeatherById(id:Int) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase(id)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }
}