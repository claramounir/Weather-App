package com.example.weatherapp.data.network

import com.example.weatherapp.Constant
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("onecall")
    suspend fun getWeatherDetalis(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") language: String,
        @Query("units") units: String,
        @Query("exclude") exclude: String ?= null,
        @Query("appid") appid: String = Constant.appId
    ): Response<WeatherResponse>
}