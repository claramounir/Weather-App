package com.example.weatherapp.alertScreen

import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite

interface OnAlertClickListener {
//    fun sendWeather(lat:Double,lon:Double)
    fun deleteAlert(alert:AlertModel)
}