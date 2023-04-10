package com.example.weatherapp.model


import com.example.weatherapp.Constant
import com.example.weatherapp.data.local.LocalInterface
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.data.network.GetFromApi
import com.example.weatherforecast.model.SharedPrefrences.SharedManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


class Repository (  private var remote: GetFromApi,var localSource: LocalInterface ) :RepositoryInterface {

    lateinit var myResponse: Response<WeatherResponse>

    companion object {
        private var instance: Repository? = null
        fun getInstance(remote: ApiResponse, localSource: LocalInterface): Repository {

            return instance ?: synchronized(this) {
                val temp = Repository(
                    remote, localSource
//                    roomDB
                )
                instance = temp
                temp
            }
        }
    }

    // functions from fav dao


    override suspend fun insertToFavorite(fav: Favourite) {
        localSource.insertToFavorite(fav)
    }

    override suspend fun deleteFromFavorite(fav: Favourite) {
        localSource.deleteFromFavorite(fav)
    }

    override suspend fun getFavorites(): Flow<List<Favourite>> {
        return localSource.getFavorites()

    }

    override suspend fun getAlert(): Flow<List<AlertModel>> {
        return localSource.getAlert()
    }

    override suspend fun insertToAlert(alert: AlertModel) {
        localSource.insertToAlert(alert)
    }

    override suspend fun deleteFromAlert(alert: AlertModel) {
        localSource.deleteFromAlert(alert)

    }

    override suspend fun getWeatherFromApi(
        lat: Double,
        lon: Double,
        exclude: String,
        appid: String
    ): Flow<Response<WeatherResponse>?> = flow {
        emit(remote.getWeatherFromApi(lat, lon, "", Constant.appId))

    }
        .flowOn(Dispatchers.IO)






    override fun saveSettings(settings: Settings) {
        SharedManger.saveSettings(settings)
    }

    override fun getSettings(): Settings? {
        return SharedManger.getSettings()

    }

    override fun saveAlertSettings(alertSettings: AlertSettings) {
        SharedManger.saveAlertSettings(alertSettings)
    }

    override fun getAlertSettings(): AlertSettings? {
        return SharedManger.getAlertSettings()
    }


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


    // functions from Api calls
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
}
