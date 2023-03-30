package com.example.weatherapp.favoriteScreen.viewModel


import Favourite
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch



class FavouriteViewModel (private val repo: Repository): ViewModel(){

    private val _favouriteList = MutableStateFlow<List<Favourite>>(emptyList())
    val favouriteList:StateFlow<List<Favourite>>
        get() = _favouriteList

    fun getFavouriteList(){
        viewModelScope.launch {
            repo.getFavourites()
                .collect{
                    _favouriteList.value = it
                }
        }
    }

    fun deleteFavourite(favourite: Favourite){
        viewModelScope.launch {
            repo.deleteFavourite(favourite)
        }
    }

    fun insertFavourite(favourite: Favourite){
        viewModelScope.launch {
            repo.insertFavourite(favourite)
        }
    }


}
