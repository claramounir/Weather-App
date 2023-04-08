package com.example.weatherapp.data.local

import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.WeatherResponse


interface LocalInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): List<Favourite>

    suspend fun getAlert():List<AlertModel>
    suspend fun insertToAlert(alert: AlertModel)
    suspend fun deleteFromAlert(alert: AlertModel):Int

    suspend fun getHome(): WeatherResponse
    suspend fun insertToHome(home: WeatherResponse)
    suspend fun deleteFromHome(): Int
}