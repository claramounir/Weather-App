package com.example.weatherapp.favoriteScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.model.Repository

class FavoriteViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // return HomeViewModel(this.repository) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        } else {
            throw IllegalArgumentException("View Model class Not found")
        }
    }
}