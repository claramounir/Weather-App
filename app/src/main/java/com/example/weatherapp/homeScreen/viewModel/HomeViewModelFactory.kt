package com.example.weatherapp.homeScreen.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.weatherapp.model.Repository

class HomeViewModelFactory(private val repository: Repository, val context: Context, val gps: MyLocation): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // return HomeViewModel(this.repository) as T

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository,context,gps) as T
        } else {
            throw IllegalArgumentException("View Model class Not found")
        }
    }
}