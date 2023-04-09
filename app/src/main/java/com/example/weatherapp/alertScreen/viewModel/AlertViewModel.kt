package com.example.weatherapp.alertScreen.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AlertViewModel (private val repo: RepositoryInterface): ViewModel(){


    var _alert: MutableStateFlow<AlertRoomState> = MutableStateFlow(AlertRoomState.Loading)
    var favWeather: StateFlow<AlertRoomState> = _alert

init {
   getAlert()
}

    fun getAlert() {


        viewModelScope.launch {
            var resonseData = repo.getAlert()
            withContext(Dispatchers.Main) {
                resonseData
                    .catch {
                        _alert.value = AlertRoomState.Failure(it)

                    }
                    .collect {
                        _alert.value = it?.let { it1 -> AlertRoomState.Success(it1)

                        }!!

                    }
            }
        }

//    private fun getWeather() {
//        viewModelScope.launch {
//            var resonseData = repo.getFavorites()
//            withContext(Dispatchers.Main) {
//                resonseData
//                    .catch {
//                        _favWeather.value = RoomState.Failure(it)
//
//                    }
//                    .collect {
//                        _favWeather.value = it?.let { it1 -> RoomState.Success(it1) }!!
//
//                    }
//
//            }
//        }
//    }
    }
      fun deleteAlert(alert: AlertModel){
    viewModelScope.launch(Dispatchers.IO){
        repo.deleteFromAlert(alert)
       getAlert()
    }
   }
    fun insertAlert(alert: AlertModel){

        viewModelScope.launch(Dispatchers.IO){
            repo.insertToAlert(alert)
            getAlert()
        }
    }


    fun getAlertSettings(): AlertSettings?{
        return repo.getAlertSettings()
    }
    fun saveAlertSettings(alertSettings: AlertSettings){
        repo.saveAlertSettings(alertSettings)
    }


}


