package com.example.weatherapp.setting.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.model.Settings


class SettingsViewModel(var repository: RepositoryInterface) : ViewModel() {
    //private var repository: Repository
    init {
     //   repository=Repository.getInstance(context)
    }
    fun saveSettings(settings: Settings?){
        if (settings != null) {
            repository.saveSettings(settings)

        }
    }
    fun getSettings():Settings?{
        return repository.getSettings()
    }
}
