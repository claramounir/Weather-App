package com.example.weatherapp.homeScreen.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Constant
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class HomeViewModel(private val repo: Repository, val context: Context, val gps: MyLocation) : ViewModel() {

    // for dialog
    private val locationProvide: MutableLiveData<Int> = MutableLiveData()
    val selectedLocProvider: LiveData<Int> get() = locationProvide
    fun selectedLocProvider(item: Int) {
        locationProvide.value = item
    }

    private val _weatherDetails = MutableStateFlow<ApiResponse<WeatherResponse>>(ApiResponse.OnLoading())
    val weatherDetails: StateFlow<ApiResponse<WeatherResponse>>
        get() = _weatherDetails

    fun getWeatherDetails(

        lat:Double,
        lon: Double,
        exclude: String ,
        units: String,
        language: String,
    ){
        Log.i("TAGHadia", "getWeatherDetails: ")
        // here the data come , i wil send it by live data
        viewModelScope.launch {
            // _weatherDetails.value = repo.getWeatherDetalis(lat, lon, exclude, units)

            // for test insertion
            repo.getWeatherDetalis(lat, lon, exclude, units, language).catch {
                _weatherDetails.value = ApiResponse.onError(it.message.toString())
            }.collect(){
                Log.i("TAGHadia", "getWeatherDetails:ay7aga ")
                    //  repo.insertFavourite(Favourite(latitude = it.lat, longitude = it.lon, city = it.timezone!!))
                    _weatherDetails.value = ApiResponse.OnSucess(it)

                }
        }
    }

}

//class HomeViewModel(private val repo: Repository, val context: Context, val gps: MyLocation) : ViewModel() {
//
//    // for dialog
//    private val locationProvide: MutableLiveData<Int> = MutableLiveData()
//    val selectedLocProvider: LiveData<Int> get() = locationProvide
//    fun selectedLocProvider(item: Int) {
//        locationProvide.value = item
//    }
//
//    val _weatherDetails = MutableLiveData<WeatherResponse>()
//
//
//fun  getLocation(context: Context){
//    gps.getLastLocation()
//    gps.data.observe(context as LifecycleOwner, Observer {
//        getWeatherDetails(it.latitude,it.longitude,Constant.appId)
//    })
//}
//
//    fun getWeatherDetails(
//       lat:Double,
//      lon: Double,
////      exclude: String ,
////        units: String,
////        language: String,
//    appid: String
//    ){
//        // here the data come , i wil send it by live data
//        viewModelScope.launch {
//
//            var resonseData = repo.getWeatherDetalis(lat,lon,appid)
//         withContext(Dispatchers.Main){
//             _weatherDetails.value = resonseData.body()
//         }
////            // for test insertion
////            repo.getWeatherDetalis(lat, lon, exclude, units, language).catch {
////                _weatherDetails.value = ApiResponse.onError(it.message.toString())
////            }.collect(){
////                    //  repo.insertFavourite(Favourite(latitude = it.lat, longitude = it.lon, city = it.timezone!!))
////                    _weatherDetails.value = ApiResponse.OnSucess
////
////                }
//
//
//
//        }
//
//    }
//}