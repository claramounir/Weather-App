package com.example.weatherapp.model

import retrofit2.Response

interface RepositoryInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): List<Favourite>

    suspend fun getAlert():List<AlertModel>
    suspend fun insertToAlert(alert: AlertModel)
    suspend fun deleteFromAlert(alert: AlertModel)
    suspend fun getWeatherFromApi(lat:Double,lon:Double,exclude:String,appid:String): Response<WeatherResponse>


    fun saveSettings(settings: Settings)
    fun getSettings(): Settings?
    fun saveAlertSettings(alertSettings: AlertSettings)
    fun getAlertSettings(): AlertSettings?

    suspend fun getHome(): WeatherResponse
    suspend fun insertToHome(home: WeatherResponse)
    suspend fun deleteFromHome(): Int

}