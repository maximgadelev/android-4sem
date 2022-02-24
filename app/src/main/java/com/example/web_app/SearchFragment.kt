package com.example.web_app
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.web_app.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment:Fragment(R.layout.fragment_search) {
    val bundle=Bundle()
    var idCity:Int = 0
    private val repository by lazy {
        WeatherRepository()
    }

    var binding: FragmentSearchBinding? = null;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fbSearch?.setOnClickListener {
           getWeather()
        }

    }

    private fun getWeather() {
        lifecycleScope.launch {
            try {
                val city = binding?.etSearch?.text.toString()
                if (binding?.etSearch?.length()==0){
                   Toast.makeText(context,"123",Toast.LENGTH_LONG).show()
                } else {
                    val response = repository.getWeatherByCity(city)
                    idCity=response.id
                    bundle.putInt("id",idCity)
                    findNavController().navigate(R.id.action_searchFragment_to_detailFragment,bundle)
                }
                } catch (ex: Exception) {
                    Toast.makeText(context,"Данный город не найден",Toast.LENGTH_LONG).show()
                }
            }
        }
}