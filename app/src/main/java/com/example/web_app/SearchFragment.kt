package com.example.web_app

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.web_app.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search) {
    val bundle = Bundle()
    var idCity: Int = 0
    private var changingLongitude: Double? = null ?: 10.0
    private var changingLatitude: Double? = null ?: 10.0
    private lateinit var locationClient: FusedLocationProviderClient

    private var mRecyclerView: RecyclerView? = null
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
        mRecyclerView = view.findViewById(R.id.rv_weather_list)
        setupLocation()
       initSearch()

    }

    private fun setupLocation() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, 100)
        } else {
            locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                changingLatitude = location?.latitude
                changingLongitude = location?.longitude
                initRecyclerView()
                if (location != null) {
                    Toast.makeText(context, "Локация обнаружена", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Локация ne обнаружена", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocation()
                } else {
                    Toast.makeText(context, "Доступ к локации запрещён", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        mRecyclerView?.run {
            lifecycleScope.launch {
                adapter = WeatherAdapter(
                    repository.getWeatherList(changingLatitude, changingLongitude, 10)
                ) {
                    bundle.putInt("id", it)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_detailFragment,
                        bundle
                    )
                }
            }
            layoutManager = GridLayoutManager(context, 2)
        }
    }


    private fun initSearch(){
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    try {
                        val queryWeather = repository.getWeatherByCity(query)
                        idCity=queryWeather.id

                        bundle.putInt("id", idCity)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_detailFragment,
                        bundle)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "Город не найден",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
})
    }
}