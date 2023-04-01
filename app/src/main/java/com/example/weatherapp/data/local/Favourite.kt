package com.example.weatherapp.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int?= null,
    val latitude: Double ?= null,
    val longitude: Double ?= null,
    val city:String ?= null
)
