package com.example.weatherapp.data.network

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response


sealed class ApiState{
    class Success (val data: Response<WeatherResponse>):ApiState()
    class Failure(val msg:Throwable):ApiState()
    object Loading:ApiState()
}