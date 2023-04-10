package com.example.weatherapp.data.dataSource

import com.example.weatherapp.data.network.GetFromApi
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response

class FakeRemoteDataSource() : GetFromApi {
    var weatherResponse: WeatherResponse = WeatherResponse()
    override suspend fun getWeatherFromApi(
        lat: Double,
        lon: Double,
        exclude: String,
        appid: String
    ): Response<WeatherResponse>? {
        return Response.success(weatherResponse)
    }
}