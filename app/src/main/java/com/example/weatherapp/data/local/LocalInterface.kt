package com.example.weatherapp.data.local

import com.example.weatherapp.model.Favourite


interface LocalInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): List<Favourite>
}