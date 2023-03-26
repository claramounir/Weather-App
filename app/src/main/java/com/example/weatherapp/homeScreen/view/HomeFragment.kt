package com.example.weatherapp.homeScreen.view

import RoomDB
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.Constant.MBAR
import com.example.weatherapp.Constant.PERMISSIN_ID
import com.example.weatherapp.R
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.Repository
import com.google.android.gms.common.api.Api
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var myViewModel: ViewModel
    lateinit var myViewModelFactory: HomeViewModelFactory
    lateinit var progressDialog: DialogFragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var geocoder: Geocoder
    private lateinit var fusedClient : FusedLocationProviderClient

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        geocoder = Geocoder(requireActivity(), Locale.getDefault())
        fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()

        myViewModelFactory = HomeViewModelFactory(Repository.getInstance(ApiResponse.getINSTANCE()))

        myViewModel =
            ViewModelProvider(this.requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
//        (myViewModel as HomeViewModel).getWeatherDetails(lat)
        (myViewModel as HomeViewModel)._weatherDetails.observe(viewLifecycleOwner) {
            _binding?.countryTxt?.text = it.timezone
            _binding?.CelsusTxt?.text = it.current?.temp.toString() + Constant.CELSIUS
            val dayhome= it.current?.dt?.let { it1 -> getCurrentDay(it1.toInt()) }
            _binding?.homeDecs?.text = it.current?.weather?.get(0)?.description
            _binding?.dateTxt?.text= dayhome
            _binding?.pressureValueTxt?.text= it.current?.temp.toString() +Constant.CELSIUS
            binding.humidityValueTxt.text = it.current?.humidity.toString() + "%"

            Glide.with(requireActivity()).load("https://openweathermap.org/img/wn/${it.current?.weather?.get(0)?.icon}@2x.png").into(binding.imgMain)

            _binding?.windValueTxt?.text = it.current?.windSpeed.toString() + Constant.WINDSPEED

            _binding?.cloudValueTxt?.text = it.current?.pressure.toString() + MBAR
            var time= formatTime(it.current?.sunrise)
            var time2= formatTime(it.current?.sunset)
            _binding?.UVValueTxt?.text = time
            _binding?.visibilityValueTxt?.text = time2
            binding.daysRv.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = DayAdapter(it.daily)
            }
            binding.hoursRv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.adapter = HourAdapter(it.hourly)

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getCurrentDay( dt: Int) : String{
        var date= Date(dt*1000L)
        var sdf= SimpleDateFormat("d")
        sdf.timeZone= TimeZone.getDefault()
        var formatedData=sdf.format(date)
        var intDay=formatedData.toInt()
        var calendar=Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,intDay)
        var format=SimpleDateFormat("EEEE")
        return format.format(calendar.time)
    }
    fun formatTime(dateObject: Long?): String {
        val date = Date(dateObject!! * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)


    }

    @SuppressLint("MissingPermission")
    private  fun  requestNewLocationDate(){
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
//        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        fusedClient.requestLocationUpdates(
            mLocationRequest , mLocationCallback,
            Looper.myLooper()
        )
    }


    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                (myViewModel as HomeViewModel).getWeatherDetails(
                    mLastLocation.latitude,
                    mLastLocation.longitude,
                    "exclude", "a62af663ada4f8dbf13318c557451a3b"
                )
            }
        }
    }


    private fun checkPermissions():Boolean{
        val result =
            ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(    requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        return result
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private  fun  requestPermissions(){
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSIN_ID)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
               requestNewLocationDate()

            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

}