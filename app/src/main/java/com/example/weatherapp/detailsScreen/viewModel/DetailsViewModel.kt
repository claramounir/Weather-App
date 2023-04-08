package com.example.weatherapp.detailsScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.ApiState
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repo: RepositoryInterface) : ViewModel() {

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
                        _weatherDetails.value= ApiState.Failure(it)
                    }
                    .collect {
                        _weatherDetails.value = it?.let { it1 -> ApiState.Success(it1) }!!

                    }
            }

        }

    }
}