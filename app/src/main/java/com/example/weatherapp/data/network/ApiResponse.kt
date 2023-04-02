package com.example.weatherapp.data.network

import RetrofitInstance
import android.util.Log
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.create
import kotlin.math.log

class ApiResponse {
 var api:ApiInterface = RetrofitInstance.retrofitInstance.create(ApiInterface::class.java)
   companion object {
      private var instance: ApiResponse? = null
      fun getINSTANCE(): ApiResponse {
         return instance ?: synchronized(this) {
            val temp = ApiResponse()
            instance = temp
            temp
         }
      }
   }
   suspend fun getWeatherFromApi(lat : Double, long:Double,exclude:String,appid:String): Response<WeatherResponse> {
var x=api.getWeatherDetalis(lat,long)
      x.body()?.icon?.let { Log.i("el data", it)
      }
      return x

   }
//    class OnLoading<T>(): ApiResponse<T>()
//    class onError<T>(var message: String): ApiResponse<T>()
}
