package com.example.web_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.web_app.response.WeatherList


class WeatherAdapter(
    private var weatherList:WeatherList,
    private val action: (Int) -> Unit
): RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    class WeatherHolder(
        itemView: View,
        private val action: (Int)->Unit
    ):RecyclerView.ViewHolder(itemView)
    {
        private val tvTemp: TextView =itemView.findViewById(R.id.tv_temp_rv)
        private val ivTemp: ImageView =itemView.findViewById(R.id.iv_temp_rv)
        private val tvRain: ImageView =itemView.findViewById(R.id.iv_rain_rv)
        private val ivWind: ImageView =itemView.findViewById(R.id.iv_wind_rv)
        private val tvTown: TextView =itemView.findViewById(R.id.tv_town_rv)

        fun bind(response: WeatherList.WeatherResponse){
            tvTemp.text=response.main.temp.toString()
            tvTown.text=response.name
            itemView.setOnClickListener {
                action(response.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
       return WeatherHolder(
           LayoutInflater.from(parent.context)
           .inflate(R.layout.weather_item,parent,false),action)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
      holder.bind(weatherList.list[position])
    }

    override fun getItemCount():Int=weatherList.list.size
    }

