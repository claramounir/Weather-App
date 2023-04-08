package com.example.weatherapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow



@Dao
interface AlertDao {

    @Query("SELECT * FROM alert")


    suspend fun getAlert(): Flow<List<AlertModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToAlert(alert: AlertModel)

    @Delete
    suspend fun deleteFromAlert(alert: AlertModel): Int

//    fun getAlerts(): Flow<List<AlertModel>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAlert(alert: AlertModel):Long
//
//    @Query("DELETE FROM alert WHERE id = :id")
//    suspend fun deleteAlert(id: Int)
//
//    @Query("SELECT * FROM alert WHERE id = :id")
//    suspend fun getAlert(id: Int):AlertModel

}