package com.example.weatherapp.homeScreen.viewModel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Constant
import com.example.weatherapp.data.network.ApiState
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repo: RepositoryInterface) : ViewModel() {

    // for dialog

    var _weatherDetails: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    var weatherDetails: StateFlow<ApiState> = _weatherDetails
    fun getWeatherDetails(
       lat:Double,
      lon: Double,
      exclude: String ,

    appid: String
    ){
        // here the data come , i wil send it by live data
        viewModelScope.launch {

            var resonseData = repo.getWeatherFromApi(lat,lon,"exclude",appid)
         withContext(Dispatchers.Main){
           resonseData
                .catch {
                    _weatherDetails.value=ApiState.Failure(it)
                }
                .collect {
                    _weatherDetails.value = it?.let { it1 -> ApiState.Success(it1) }!!

                }
         }

        }

    }
}
//fun getProduct(){
//    viewModelScope.launch(Dispatchers.IO) {
//        val response = _repo.allProducts()
//        withContext(Dispatchers.Main) {
//            response
//                .catch {
//                    _product.value=ApiState.Failure(it)
//                }
//                .collect {
//                    _product.value = ApiState.Success(it)
//
//                }
//        }
//    }
//}
