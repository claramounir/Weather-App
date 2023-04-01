package com.example.weatherapp.model

import RetrofitInstance
import android.content.Context
import android.util.Log
import com.example.weatherapp.data.local.Favourite
import com.example.weatherapp.data.local.RoomDB
import com.example.weatherapp.data.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//import Favourite
//import RetrofitInstance
//import RoomDB
//import android.content.Context
//
//import com.example.weatherapp.data.network.ApiInterface
//import com.example.weatherapp.data.network.ApiResponse
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import okhttp3.Response





class Repository (context: Context){
    private lateinit var remote: ApiInterface
    private lateinit var room: RoomDB

    init{
        remote = RetrofitInstance().apiCall()
        room = RoomDB.invoke(context) as RoomDB
    }

    // functions from fav dao

    fun getFavourites(): Flow<List<Favourite>> {
        return room.favouriteDao().getFavourites()
    }

    suspend fun insertFavourite(favourite: Favourite){
        room.favouriteDao().insertFavourite(favourite)
    }


    suspend fun deleteFavourite(favourite: Favourite){
        room.favouriteDao().deleteFavourite(favourite)
    }


    // alerts

    fun getAlerts(): Flow<List<AlertModel>>{
        return room.alertDao().getAlerts()
    }


    suspend fun insertAlert(alert: AlertModel):Long{
        return room.alertDao().insertAlert(alert)
    }


    suspend fun deleteAlert(id: Int){
        room.alertDao().deleteAlert(id)
    }


    suspend fun getAlert(id: Int): AlertModel{
        return room.alertDao().getAlert(id)
    }




    // functions from Api calls
    fun getWeatherDetalis(
        lat: Double,
        lon: Double,
        language: String ,
        units: String ,
        exclude: String ?= null,

        ) = flow {
        val response =  remote.getWeatherDetalis(lat = lat, lon = lon, exclude = exclude, units = units, language = language)
        if(response.isSuccessful){
            Log.i("TAGRun", "getWeatherDetalis: " + response.body()?.current?.weather?.get(0)?.description)
            emit(response.body() ?: WeatherResponse() )
        }else{
            println("coca")
            emit( WeatherResponse() ) }
    }
}
//class Repository (  private var remote: ApiResponse,
//                    private var room: RoomDB
//                 ) {
//
//    companion object{
//        private var instance:Repository?=null
//        fun getInstance(
//            apiResponse: ApiResponse,
//            roomDB: RoomDB,
//localSourceInterface: LocalSourceInterface
//        ):Repository{
//            return instance?: synchronized(this){
//                val temp =Repository(
//                  apiResponse,
//                    roomDB
//                )
//                instance=temp
//                temp
//            }
//        }
//    }
//
//    // functions from fav dao
//
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
////
////    fun getAlerts(): Flow<List<AlertModel>> {
////        return room.alertDao().getAlerts()
////    }
////
////
////    suspend fun insertAlert(alert: AlertModel): Long {
////        return room.alertDao().insertAlert(alert)
////    }
////
////
////    suspend fun deleteAlert(id: Int) {
////        room.alertDao().deleteAlert(id)
////    }
////
////
////    suspend fun getAlert(id: Int): AlertModel {
////        return room.alertDao().getAlert(id)
////    }
//
//
//    // functions from Api calls
//    suspend fun getWeatherDetalis(
//        lat: Double,
//        lon: Double,
////        language: String,
////        units: String,
////        exclude: String? = null,
//        appid:String
//
//    ): retrofit2.Response<WeatherResponse> {
//        return remote.OnSucess(lat,lon)
//    }
//}
