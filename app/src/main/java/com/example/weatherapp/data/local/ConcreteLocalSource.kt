package com.example.weatherapp.data.local


import android.content.Context
import com.example.weatherapp.model.*

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
    private val daoHome: HomeDao by lazy {

        val db3:RoomDB= RoomDB.getInstance(context)
        db3.getHomeDao()
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

    override suspend fun getAlert(): List<AlertModel> {
        return daoAlert.getAlert()
    }

    override suspend fun insertToAlert(alert: AlertModel) {
        return daoAlert.insertToAlert(alert)
    }

    override suspend fun deleteFromAlert(alert: AlertModel): Int {
        return daoAlert.deleteFromAlert(alert)
    }

    override suspend fun getHome(): WeatherResponse {
       return daoHome.getHome()
    }

    override suspend fun insertToHome(home: WeatherResponse) {
        return daoHome.insertToHome(home)
    }

    override suspend fun deleteFromHome(): Int {
        return daoHome.deleteFromHome()
    }
}