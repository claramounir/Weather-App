package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.AlertDao
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite

//class RoomDB {
//}

@Database(entities = arrayOf(Favourite::class, AlertModel::class), version = 4 )

abstract class RoomDB : RoomDatabase() {
    abstract fun getWeathersDao(): FavouriteDao
    abstract fun getAlertDao(): AlertDao
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