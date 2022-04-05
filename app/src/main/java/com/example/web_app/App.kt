package com.example.web_app

import android.app.Application
import com.example.web_app.di.AppComponent
import com.example.web_app.di.DaggerAppComponent
import com.example.web_app.di.module.AppModule
import com.example.web_app.di.module.NetModule

class App:Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }
}