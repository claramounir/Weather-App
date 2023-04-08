package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert", primaryKeys = ["startTime","endTime"])
data class AlertModel(

//    @PrimaryKey(autoGenerate = true)
//    val id:Int? = null,
    var startTime: Long ,
    var endTime: Long ,
    var startDate: Long ?= null,
    var endDate: Long ?= null,
    var latitude: Double ?= null,
    var longitude: Double ?= null,
    var cityName:String?=null
)