package com.example.weatherapp.detailsScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repo: RepositoryInterface) : ViewModel() {

    val _weatherDetails = MutableLiveData<WeatherResponse>()
    var weatherDetails: LiveData<WeatherResponse> = _weatherDetails
    fun getWeatherDetails(
        lat: Double,
        lon: Double,
        exclude: String,

        appid: String
    ) {
        // here the data come , i wil send it by live data
        viewModelScope.launch {

            var resonseData = repo.getWeatherFromApi(lat, lon, "exclude", appid)
            withContext(Dispatchers.Main) {
                _weatherDetails.value = resonseData.body()
            }
        }
    }
}