package com.example.weatherapp.homeScreen.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.Constant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.*

class MyLocation(val context: Context) {
    private lateinit var fusedClient : FusedLocationProviderClient
    private var _data : MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    val data: LiveData<LatLng> = _data

    @SuppressLint("MissingPermission")
    private  fun  requestNewLocationDate(){
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
        fusedClient = LocationServices.getFusedLocationProviderClient(context as Activity)
        fusedClient.requestLocationUpdates(
            mLocationRequest , mLocationCallback,
            Looper.myLooper()
        )
    }


    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
_data.postValue(
    LatLng(  mLastLocation.latitude,mLastLocation.longitude)

)
                // stop observation
                stopLocationUpdates()
            }
        }
    }



    private fun checkPermissions():Boolean{
        val result =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission( context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        return result
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    private  fun  requestPermissions(){
        ActivityCompat.requestPermissions(
          context as Activity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), Constant.PERMISSIN_ID
        )
    }
    private fun stopLocationUpdates() {
        fusedClient.removeLocationUpdates(mLocationCallback)
    }

    @SuppressLint("MissingPermission")
 fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationDate()

            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                ContextCompat.startActivity(context, intent, null)
            }
        } else {
            requestPermissions()
        }
    }
    fun updateLocationFromGPS() {

        fusedClient = LocationServices.getFusedLocationProviderClient(context)
        if(checkPermissions()){
            if(isLocationEnabled()){
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
                getLastLocation()
            } else{
                Toast.makeText(context, "Should Enable Location", Toast.LENGTH_LONG).show()
            }

        }else{
            requestPermissions()
        }
    }
}