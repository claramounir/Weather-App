package com.example.weatherapp.model

import Favourite
import RetrofitInstance
import RoomDB
import android.content.Context

import com.example.weatherapp.data.network.ApiInterface
import com.example.weatherapp.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response

class Repository (  private var remote: ApiResponse
//                    private var room: RoomDB
                 ) {

    companion object{
        private var instance:Repository?=null
        fun getInstance(
            apiResponse: ApiResponse,
//            roomDB: RoomDB
//localSourceInterface: LocalSourceInterface
        ):Repository{
            return instance?: synchronized(this){
                val temp =Repository(
                  apiResponse
//                    roomDB
                )
                instance=temp
                temp
            }
        }
    }

    // functions from fav dao

//    fun getFavourites(): Flow<List<Favourite>> {
//        return room.favouriteDao().getFavourites()
//    }
//
//    suspend fun insertFavourite(favourite: Favourite) {
//        room.favouriteDao().insertFavourite(favourite)
//    }
//
//
//    suspend fun deleteFavourite(favourite: Favourite) {
//        room.favouriteDao().deleteFavourite(favourite)
//    }
//
//
//    // alerts
//
//    fun getAlerts(): Flow<List<AlertModel>> {
//        return room.alertDao().getAlerts()
//    }
//
//
//    suspend fun insertAlert(alert: AlertModel): Long {
//        return room.alertDao().insertAlert(alert)
//    }
//
//
//    suspend fun deleteAlert(id: Int) {
//        room.alertDao().deleteAlert(id)
//    }
//
//
//    suspend fun getAlert(id: Int): AlertModel {
//        return room.alertDao().getAlert(id)
//    }
//

    // functions from Api calls
    suspend fun getWeatherDetalis(
//        lat: Double,
//        lon: Double,
//        language: String,
//        units: String,
//        exclude: String? = null,

    ): retrofit2.Response<WeatherResponse> {
        return remote.OnSucess()
    }
}
