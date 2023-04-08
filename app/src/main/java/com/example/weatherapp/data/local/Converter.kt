package com.example.weatherapp.data.local
import androidx.room.TypeConverter
import com.example.weatherapp.model.*
import com.google.gson.Gson

class Converter {

    @TypeConverter
//    fun fromStringToWeather(weather:String): WeatherResponse?{
//        // pasre string to object
//
//        return weather?.let{
//            Gson().fromJson(it, WeatherResponse::class.java)
//        }
//    }
//
//    @TypeConverter
//    fun fromWeatherToString(weather:WeatherResponse):String?{
//        // pasre object to string
//
//        return weather?.let{
//            Gson().toJson(it)
//        }
//    }

    fun listOfCurrentToJson (value: List<Current>) = Gson().toJson(value)
    @TypeConverter
    fun jsonToListOfCurrent(value: String ) =Gson().fromJson(value,Array<Current>::class.java).toList()
    @TypeConverter
    fun listOfDailyToJson(value: List<Daily>) = Gson().toJson(value)
    @TypeConverter
    fun jsonToListOfDaily(value: String ) =Gson().fromJson(value,Array<Daily>::class.java).toList()
    @TypeConverter
    fun listOfWeatherToJson(value: List<Weather> ) =Gson().toJson(value)
    @TypeConverter
    fun JsonToListOfWeather(value: String ) =Gson().fromJson(value,Array<Weather>::class.java).toList()
    @TypeConverter
    fun listOfAlertsToJson(value: List<Alerts> ) =Gson().toJson(value)
    @TypeConverter
    fun JsonToListOfAlerts(value: String ) =Gson().fromJson(value,Array<Alerts>::class.java).toList()
    }

