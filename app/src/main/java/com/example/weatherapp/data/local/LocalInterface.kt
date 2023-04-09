package com.example.weatherapp.data.local

import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.flow.Flow


interface LocalInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): Flow<List<Favourite>>

    suspend fun getAlert():Flow<List<AlertModel>>
    suspend fun insertToAlert(alert: AlertModel)
    suspend fun deleteFromAlert(alert: AlertModel):Int
}