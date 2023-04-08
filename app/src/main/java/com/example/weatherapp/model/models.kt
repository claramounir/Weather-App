package com.example.weatherapp.model

import com.example.weatherapp.Constant
import com.google.android.filament.Entity
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList


// models of api
data class Weather(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
){
    constructor():this(null,null,null,null)
}


data class Current (
    @SerializedName("dt") var dt: Long,
    @SerializedName("sunrise") var sunrise: Long? = null,
    @SerializedName("sunset") var sunset: Long? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("dew_point") var dewPoint: Double? = null,
    @SerializedName("uvi") var uvi: Double? = null,
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind_speed") var windSpeed: Double? = null,
    @SerializedName("wind_deg") var windDeg: Int? = null,
    @SerializedName("wind_gust") var windGust: Double? = null,
    @SerializedName("weather") var weather: List<Weather> = arrayListOf()

){
    constructor():this(0L , 0L , 0L ,0.0 ,0.0, 0,0,0.0,0.0,0,0,0.0,0,0.0, listOf() )
}

//data class Hourly(
//    @SerializedName("dt") var dt: Long,
//    @SerializedName("temp") var temp: Double? = null,
//    @SerializedName("feels_like") var feelsLike: Double? = null,
//    @SerializedName("pressure") var pressure: Int? = null,
//    @SerializedName("humidity") var humidity: Int? = null,
//    @SerializedName("dew_point") var dewPoint: Double? = null,
//    @SerializedName("uvi") var uvi: Double? = null,
//    @SerializedName("clouds") var clouds: Int? = null,
//    @SerializedName("visibility") var visibility: Int? = null,
//    @SerializedName("wind_speed") var windSpeed: Double? = null,
//    @SerializedName("wind_deg") var windDeg: Int? = null,
//    @SerializedName("wind_gust") var windGust: Double? = null,
//    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
//    @SerializedName("pop") var pop: Double? = null
//)

data class FeelsLike(

    @SerializedName("day") var day: Double? = null,
    @SerializedName("night") var night: Double? = null,
    @SerializedName("eve") var eve: Double? = null,
    @SerializedName("morn") var morn: Double? = null

)

data class Temp(

    @SerializedName("day") var day: Double? = null,
    @SerializedName("min") var min: Double? = null,
    @SerializedName("max") var max: Double? = null,
    @SerializedName("night") var night: Double? = null,
    @SerializedName("eve") var eve: Double? = null,
    @SerializedName("morn") var morn: Double? = null

){
    constructor():this(0.0,0.0,0.0,0.0,0.0,0.0)
}

data class Daily(

    @SerializedName("dt") var dt: Long,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("moonrise") var moonrise: Int? = null,
    @SerializedName("moonset") var moonset: Int? = null,
    @SerializedName("moon_phase") var moonPhase: Double? = null,
    @SerializedName("temp") var temp: Temp? = Temp(),
    @SerializedName("feels_like") var feelsLike: FeelsLike? = FeelsLike(),
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("dew_point") var dewPoint: Double? = null,
    @SerializedName("wind_speed") var windSpeed: Double? = null,
    @SerializedName("wind_deg") var windDeg: Int? = null,
    @SerializedName("wind_gust") var windGust: Double? = null,
    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("clouds") var clouds: Int? = null,
    @SerializedName("pop") var pop: Double? = null,
    @SerializedName("uvi") var uvi: Double? = null

)

data class Alerts(
    @SerializedName("sender_name") var senderName: String? = null,
    @SerializedName("event") var event: String? = null,
    @SerializedName("start") var start: Long? = null,
    @SerializedName("end") var end: Long? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("tags") var tags: List<String>
){
    constructor():this(null,null,null,null,null, listOf())
}

//data class WeatherResponse(
//    val current: Current ?= null,
//    val daily: List<Daily> = emptyList(),
//    val hourly: List<Hourly> = emptyList(),
//    val lat: Double ?= null,
//    val lon: Double ?= null,
//    val timezone: String ?= null,
//    val timezone_offset: Int ?= null,
//    val alerts: List<Alerts>?=null,
//    val icon: String? = null
//
//)

data class Settings(
    var lang:String= Constant.LANG_EN,
     var isMap:Boolean=false,
     var unit:String=Constant.UNITS_DEFAULT,
     var lat:Double=0.0,
     var lon:Double=0.0)


data class AlertSettings (
    var lat:Double=36.4761,
    var lon:Double=-119.4432,
    var isALarm:Boolean=true,
    var isNotification:Boolean=false
)