package com.example.web_app.di.module

import com.example.web_app.domain.WeatherRepository
import com.example.web_app.domain.usecase.GetWeatherByCityUseCase
import com.example.web_app.domain.usecase.GetWeatherByIdUseCase
import com.example.web_app.domain.usecase.GetWeatherListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetByCityUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByCityUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideGetByIdUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByIdUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideListUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherListUseCase(weatherRepository)
}
