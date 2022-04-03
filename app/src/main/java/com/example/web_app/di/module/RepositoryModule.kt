package com.example.web_app.di.module

import com.example.web_app.data.WeatherRepositoryImpl
import com.example.web_app.domain.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}