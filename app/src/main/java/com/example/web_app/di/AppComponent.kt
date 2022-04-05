package com.example.web_app.di

import com.example.web_app.di.module.AppModule
import com.example.web_app.di.module.NetModule
import com.example.web_app.di.module.RepositoryModule
import com.example.web_app.di.module.ViewModelModule
import com.example.web_app.presentation.ui.MainActivity
import com.example.web_app.presentation.ui.fragment.DetailFragment
import com.example.web_app.presentation.ui.fragment.SearchFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchFragment: SearchFragment)
    fun inject(detailFragment: DetailFragment)
}