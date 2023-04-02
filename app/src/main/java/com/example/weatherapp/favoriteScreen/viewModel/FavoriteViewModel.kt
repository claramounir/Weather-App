package com.example.weatherapp.favoriteScreen.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel (private val repo: RepositoryInterface): ViewModel(){

private  var _favWeather :MutableLiveData<List<Favourite>> = MutableLiveData<List<Favourite>>()
    val favWeather: LiveData<List<Favourite>> = _favWeather

init {
    getWeather()
}

    private fun getWeather() {
        viewModelScope.launch(Dispatchers.IO){
            _favWeather.postValue(repo.getFavorites())
        }
    }

      fun deleteFavWeather(fav: Favourite){
    viewModelScope.launch(Dispatchers.IO){
        repo.deleteFromFavorite(fav)
        getWeather()
    }
   }
    fun insertFavWeather(fav:Favourite){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertToFavorite(fav)
            getWeather()
        }
    }

  }
