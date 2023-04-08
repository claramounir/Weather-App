package com.example.weatherapp.setting.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repository

class SettingsViewModelFactory(val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        return if (modelClass.isAssignableFrom(SettingsViewModel::class.java))
        {
            SettingsViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("View modle class not found")
        }
    }
}