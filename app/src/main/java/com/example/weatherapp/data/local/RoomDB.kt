package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.*
import com.example.weatherapp.model.*

//class RoomDB {
//}

@Database(entities = arrayOf(Favourite::class, AlertModel::class,WeatherResponse::class ), version = 6 )
@TypeConverters(Converter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun getWeathersDao(): FavouriteDao
    abstract fun getAlertDao(): AlertDao
    abstract fun getHomeDao(): HomeDao

    companion object{
        @Volatile
        private var INSTANCE: RoomDB? = null
        fun getInstance (ctx: Context): RoomDB{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, RoomDB::class.java, "favname3")
                    .build()
                INSTANCE = instance
                instance }
        }
    }
}