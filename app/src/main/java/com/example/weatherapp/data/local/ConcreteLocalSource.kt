package com.example.weatherapp.data.local


import android.content.Context
import com.example.weatherapp.model.Favourite

class ConcreteLocalSource private constructor(context: Context): LocalInterface {

    companion object{
        @Volatile
        private var INSTANCE :ConcreteLocalSource?=null

        fun getInstance(context: Context): ConcreteLocalSource {
            return INSTANCE?: synchronized(this){
                val temp = ConcreteLocalSource(context)
                INSTANCE=temp
                temp
            }
        }
    }
    private val dao: FavouriteDao by lazy {

        val db:RoomDB= RoomDB.getInstance(context)
        db.getWeathersDao()
    }

    override suspend fun insertToFavorite(fav: Favourite) {
        dao.insertToFavorite(fav)
    }

    override suspend fun deleteFromFavorite(fav: Favourite) {
        dao.deleteFromFavorite(fav)
    }

    override suspend fun getFavorites(): List<Favourite> {
       return dao.getFavorites()
    }
}