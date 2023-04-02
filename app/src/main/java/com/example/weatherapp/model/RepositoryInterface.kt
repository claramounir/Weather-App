package com.example.weatherapp.model

import retrofit2.Response

interface RepositoryInterface {
    suspend fun insertToFavorite(fav: Favourite)
    suspend fun deleteFromFavorite(fav: Favourite)
    suspend fun getFavorites(): List<Favourite>
    suspend fun getWeatherFromApi(lat:Double,lon:Double,exclude:String,appid:String): Response<WeatherResponse>

}