package com.example.weatherapp.data.local


import android.content.Context
import com.example.weatherapp.model.AlertDao
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource  constructor(context: Context): LocalInterface {

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
    private val daoAlert: AlertDao by lazy {

        val db:RoomDB= RoomDB.getInstance(context)
        db.getAlertDao()
    }

    override suspend fun insertToFavorite(fav: Favourite) {
        dao.insertToFavorite(fav)
    }

    override suspend fun deleteFromFavorite(fav: Favourite) {
        dao.deleteFromFavorite(fav)
    }

    override suspend fun getFavorites(): Flow<List<Favourite>> {
       return dao.getFavorites()
    }

    override suspend fun getAlert(): Flow<List<AlertModel>> {
        return daoAlert.getAlert()
    }

    override suspend fun insertToAlert(alert: AlertModel) {
        return daoAlert.insertToAlert(alert)
    }

    override suspend fun deleteFromAlert(alert: AlertModel): Int {
        return daoAlert.deleteFromAlert(alert)
    }
}