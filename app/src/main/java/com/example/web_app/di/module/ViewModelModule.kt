package com.example.web_app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.web_app.AppViewModelFactory
import com.example.web_app.di.ViewModelKey
import com.example.web_app.presentation.viewModel.DetailFragmentViewModel
import com.example.web_app.presentation.viewModel.SearchFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel::class)
    fun bindSearchFragmentViewModel(
        viewModel: SearchFragmentViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel::class)
    fun bindDetailFragmentViewModel(
        viewModel: DetailFragmentViewModel
    ): ViewModel
}