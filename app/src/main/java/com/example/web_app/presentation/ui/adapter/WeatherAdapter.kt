package com.example.web_app.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.web_app.R
import com.example.web_app.domain.entity.Weather


class WeatherAdapter(
    private var weatherList: List<Weather>,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    class WeatherHolder(
        itemView: View,
        private val action: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTemp: TextView = itemView.findViewById(R.id.tv_temp_rv)
        private val tvTown: TextView = itemView.findViewById(R.id.tv_town_rv)

        fun bind(response: Weather) {
            tvTemp.text = response.temp.toString() + "°С"
            tvTown.text = response.name
            colorizeTemp(response.temp, tvTemp, itemView)
            itemView.setOnClickListener {
                action(response.id)
            }
        }

        private fun colorizeTemp(temp: Double, textView: TextView, itemView: View) {
            when (temp) {
                in 20.0..60.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperature_20),
                )
                in 10.0..20.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperature_10),

                    )
                in 0.0..10.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperature_0),

                    )
                in -10.0..0.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperatureCold_10),

                    )
                in -20.0..-10.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperatureCold_20),

                    )
                in -50.0..-20.0 -> textView.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.temperatureCold_50),

                    )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_item, parent, false), action
        )
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size


}


