package com.example.weatherapp.model

import androidx.room.Embedded
import androidx.room.Entity


@Entity(tableName = "Home", primaryKeys = ["hourly","daily"])
data class WeatherResponse(
    @Embedded
    val current: Current ?,
    val daily: List<Daily> = emptyList(),
    val hourly: List<Current> = emptyList(),
    val lat: Double ?= null,
    val lon: Double ?= null,
    val timezone: String ?= null,
    val timezone_offset: Int ?= null,
    val alerts: List<Alerts>?=null,
    val icon: String? = null
){
    constructor():this(null , listOf(), listOf(),0.0,0.0, null , null,listOf(),null)
}

