package com.example.web_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.web_app.di.DIContainer
import com.example.web_app.presentation.viewModel.DetailFragmentViewModel
import com.example.web_app.presentation.viewModel.SearchFragmentViewModel

class ViewModelFactory(
    private val di: DIContainer,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SearchFragmentViewModel::class.java) ->
                SearchFragmentViewModel(di.getWeatherListUseCase,di.getWeatherByCityUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(DetailFragmentViewModel::class.java) ->
                DetailFragmentViewModel(di.getWeatherByIdUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}