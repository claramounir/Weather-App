package com.example.weatherapp.data.dataSource

import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeRepo :RepositoryInterface {

    var favList: MutableList<Favourite> = mutableListOf()
    var alertList: MutableList<AlertModel> = mutableListOf()


    override suspend fun insertToFavorite(fav: Favourite) {
        favList.add(fav)

    }

    override suspend fun deleteFromFavorite(fav: Favourite) {
        favList.remove(fav)
    }

    override suspend fun getFavorites(): Flow<List<Favourite>> {
        val myflow = flow {
            val myFavList = favList.toList()
            emit(myFavList)
        }
        return myflow

            }

    override suspend fun getAlert(): Flow<List<AlertModel>> {
        val myflow = flow {
            val myFavList = alertList.toList()
            emit(myFavList)
        }
        return myflow
    }

    override suspend fun insertToAlert(alert: AlertModel) {
alertList.add(alert)
    }

    override suspend fun deleteFromAlert(alert: AlertModel) {
       alertList.remove(alert)
    }

    override suspend fun getWeatherFromApi(
        lat: Double,
        lon: Double,
        exclude: String,
        appid: String
    ): Flow<Response<WeatherResponse>?> {
        TODO("Not yet implemented")
    }

    override fun saveSettings(settings: Settings) {
        TODO("Not yet implemented")
    }

    override fun getSettings(): Settings? {
        TODO("Not yet implemented")
    }

    override fun saveAlertSettings(alertSettings: AlertSettings) {
        TODO("Not yet implemented")
    }

    override fun getAlertSettings(): AlertSettings? {
        TODO("Not yet implemented")
    }
}