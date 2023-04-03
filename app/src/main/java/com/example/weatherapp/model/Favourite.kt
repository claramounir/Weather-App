
package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table", primaryKeys = ["latitude","longitude"])

data class Favourite(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int?= null,
    var latitude: Double,
    var longitude: Double,
    var city:String ?= null
)
