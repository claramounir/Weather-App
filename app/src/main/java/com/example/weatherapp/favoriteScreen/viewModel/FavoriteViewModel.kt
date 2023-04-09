package com.example.weatherapp.favoriteScreen.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.ApiState
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteViewModel (private val repo: RepositoryInterface): ViewModel(){


    var _favWeather: MutableStateFlow<RoomState> = MutableStateFlow(RoomState.Loading)
    var favWeather: StateFlow<RoomState> = _favWeather

init {
    getWeather()
}


        fun getWeather() {
            viewModelScope.launch {
                var resonseData = repo.getFavorites()
                withContext(Dispatchers.Main) {
                    resonseData
                        .catch {
                            _favWeather.value = RoomState.Failure(it)

                        }
                        .collect {
                            _favWeather.value = it?.let { it1 -> RoomState.Success(it1) }!!

                        }

                }
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
