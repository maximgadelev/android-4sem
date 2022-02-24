package com.example.web_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.web_app.databinding.FragmentDetailBinding
import com.example.web_app.response.WeatherResponse
import kotlinx.coroutines.launch

class DetailFragment:Fragment(R.layout.fragment_detail) {
    var response:WeatherResponse?=null
    private val repository by lazy {
        WeatherRepository()
    }
    var binding: FragmentDetailBinding? = null;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id =arguments?.getInt("id")
        id?.let { getWeather(it)}
    }
    private fun getWeather(id:Int){
        lifecycleScope.launch {
             response=repository.getWeatherById(id)
            binding?.tvCity?.text=response?.name
        }
    }
}