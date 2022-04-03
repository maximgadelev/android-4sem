package com.example.web_app.presentation.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.web_app.R
import com.example.web_app.AppViewModelFactory
import com.example.web_app.databinding.FragmentSearchBinding
import com.example.web_app.presentation.ui.MainActivity
import com.example.web_app.presentation.ui.adapter.WeatherAdapter
import com.example.web_app.presentation.viewModel.SearchFragmentViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val CONST_LONGITUDE = 10.34
    private val CONST_LATITUDE = 12.35
    val bundle = Bundle()
    var idCity: Int = 0
    private var changingLongitude: Double = CONST_LONGITUDE
    private var changingLatitude: Double = CONST_LATITUDE
    private lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var factoryApp: AppViewModelFactory
    private val viewModel: SearchFragmentViewModel by viewModels {
        factoryApp
    }
    private var mRecyclerView: RecyclerView? = null
    private var weatherAdapter: WeatherAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
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
        initObservers()
        setupLocation()
        mRecyclerView = view.findViewById(R.id.rv_weather_list)
        mRecyclerView?.layoutManager = GridLayoutManager(context, 2)
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
                if (location != null) {
                    changingLongitude = location.longitude
                    changingLatitude = location.latitude
                    Toast.makeText(context, "Локация найдена", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Локация не найдена", Toast.LENGTH_LONG).show()
                }
                viewModel.getWeatherList(changingLatitude, changingLongitude)
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


    private fun initSearch() {
        binding?.searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    viewModel.getWeatherForCity(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    fun initObservers() {
        viewModel.weatherList.observe(viewLifecycleOwner) { list ->
            list.fold(onSuccess = {
                weatherAdapter = WeatherAdapter(
                    it
                ) {
                    bundle.putInt("id", it)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_detailFragment,
                        bundle
                    )
                }
                mRecyclerView?.adapter = weatherAdapter
            }, onFailure = {
                Toast.makeText(context, "123", Toast.LENGTH_LONG)
            }
            )
        }

        viewModel.weather.observe(viewLifecycleOwner) { city ->
            city.fold(onSuccess = {
                bundle.putInt("id", it.id)
                findNavController().navigate(
                    R.id.action_searchFragment_to_detailFragment,
                    bundle
                )
            }, onFailure = {
                Toast.makeText(
                    context,
                    "Город не найден",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }
}
