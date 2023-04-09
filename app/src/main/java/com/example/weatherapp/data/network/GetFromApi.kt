package com.example.weatherapp.data.network

import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response

interface GetFromApi {
    suspend fun getWeatherFromApi(lat:Double,lon:Double,exclude:String,appid:String): Response<WeatherResponse>?
}