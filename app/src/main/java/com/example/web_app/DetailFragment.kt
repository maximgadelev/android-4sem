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
import java.text.SimpleDateFormat

class DetailFragment:Fragment(R.layout.fragment_detail) {
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
             val response=repository.getWeatherById(id)
            setWeathersProperties(response)
        }
    }
    private  fun setWeathersProperties(response: WeatherResponse){
        binding?.tvTemp?.text=response.main.temp.toString()+ "°С"
        binding?.tvSunrise?.text= SimpleDateFormat("HH:mm").format(response.sys.sunrise*1000)
        binding?.tvSunset?.text= SimpleDateFormat("HH:mm").format(response.sys.sunset*1000)
        binding?.tvCity?.text=response.name
        binding?.pressureTv?.text=response.main.pressure.toString() + "PA"
        binding?.tvHumidity?.text=response.main.humidity.toString() + "%"
        binding?.tvMaxTemp?.text=   "Max temp " + response.main.tempMax.toString()  +  "°С"
        binding?.tvMinTemp?.text="Min temp "+response.main.tempMin.toString()  + "°С"
        binding?.tvWind?.text=response.wind.speed.toString() + "m/s"
        binding?.tvDirect?.text=when(response.wind.deg){
            in 0..22 -> "N"
            in 23..67 -> "N-E"
            in 68..112 -> "E"
            in 113..157 -> "S-E"
            in 158..202 -> "S"
            in 203..247 -> "S-W"
            in 248..292 -> "S"
            in 293..337 -> "N-W"
            in 337..361 -> "N"
            else -> "4to-t0 ne to"
        }
    }
}