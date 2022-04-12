package com.example.web_app.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.web_app.R
import com.example.web_app.databinding.FragmentDetailBinding
import com.example.web_app.domain.entity.Weather
import com.example.web_app.presentation.viewModel.DetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {


    var binding: FragmentDetailBinding? = null
    private val viewModel: DetailFragmentViewModel by viewModels()

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
        initObservers()
        val id = arguments?.getInt("id")
        id?.let { getWeather(it) }
    }

    private fun getWeather(id: Int) {
        lifecycleScope.launch {
            viewModel.getWeatherById(id)
        }
    }

    private fun setWeathersProperties(response: Weather) {
        binding?.tvTemp?.text = response.temp.toString()
        binding?.tvSunrise?.text = SimpleDateFormat("HH:mm").format(response.sunrise * 1000)
        binding?.tvSunset?.text = SimpleDateFormat("HH:mm").format(response.sunset * 1000)
        binding?.tvCity?.text = response.name
        binding?.pressureTv?.text = response.pressure.toString() + "PA"
        binding?.tvHumidity?.text = response.humidity.toString() + "%"
        binding?.tvMaxTemp?.text = "Max temp " + response.tempMax.toString() + "°С"
        binding?.tvMinTemp?.text = "Min temp " + response.tempMin.toString() + "°С"
        binding?.tvWind?.text = response.speed.toString() + "m/s"
        binding?.tvDirect?.text = when (response.deg) {
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

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) {
            it.fold(onSuccess = {
                setWeathersProperties(it)
            }, onFailure = {
                Log.e("Error", "error")
            })
        }
    }
}