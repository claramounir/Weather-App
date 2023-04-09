package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): Flow<List<Favourite>>

    suspend fun getAlert():Flow<List<AlertModel>>
    suspend fun insertToAlert(alert: AlertModel)
    suspend fun deleteFromAlert(alert: AlertModel)
    suspend fun getWeatherFromApi(lat:Double,lon:Double,exclude:String,appid:String): Flow<Response<WeatherResponse>?>


    fun saveSettings(settings: Settings)
    fun getSettings(): Settings?
    fun saveAlertSettings(alertSettings: AlertSettings)
    fun getAlertSettings(): AlertSettings?

}