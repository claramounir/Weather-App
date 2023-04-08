package com.example.weatherapp.alertScreen.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlertViewModel (private val repo: RepositoryInterface): ViewModel(){

private  var _alert :MutableLiveData<List<AlertModel>> = MutableLiveData<List<AlertModel>>()
    val favWeather: LiveData<List<AlertModel>> = _alert

init {
   getAlert()
}

    fun getAlert() {
        viewModelScope.launch(Dispatchers.IO){
            _alert.postValue(repo.getAlert())
        }
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


