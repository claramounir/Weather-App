package com.example.weatherapp.data.dataSource

import com.example.weatherapp.data.local.LocalInterface
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource ():LocalInterface {
     var alertList: MutableList<AlertModel> = mutableListOf()
    var favList: MutableList<Favourite> = mutableListOf()



    override suspend fun insertToFavorite(fav: Favourite) {
        favList.add(fav)

    }

    override suspend fun deleteFromFavorite(fav: Favourite) {
favList.remove(fav)

    }

    override suspend fun getFavorites(): Flow<List<Favourite>> = flow {
        emit(favList)

    }

    override suspend fun getAlert(): Flow<List<AlertModel>> = flow {
      emit(alertList)

    }

    override suspend fun insertToAlert(alert: AlertModel) {
        alertList.add(alert)


    }

    override suspend fun deleteFromAlert(alert: AlertModel): Int {
        alertList.remove(alert)
        return 1

    }
}