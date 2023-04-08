package com.example.weatherapp.model

import androidx.room.*

@Dao
interface HomeDao {

    @Query("SELECT * FROM Home")

    suspend fun getHome(): WeatherResponse

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToHome(home: WeatherResponse)

   @Query("DELETE FROM Home")
    suspend fun deleteFromHome(): Int
}