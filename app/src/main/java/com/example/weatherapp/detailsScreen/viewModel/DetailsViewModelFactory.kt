package com.example.weatherapp.detailsScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.model.Repository

class DetailsViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // return DetailsViewModel(this.repository) as T
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository) as T
        } else {
            throw IllegalArgumentException("View Model class Not found")
        }
    }
}