package com.example.weatherapp.favoriteScreen.view

import com.example.weatherapp.model.Favourite

interface OnFavClickListener {
    fun sendWeather(lat:Double,lon:Double)
    fun deleteWeather(fav:Favourite)
}