package com.example.weatherapp.alertScreen.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainDispatchers
import com.example.weatherapp.data.dataSource.FakeLocalDataSource
import com.example.weatherapp.data.dataSource.FakeRemoteDataSource
import com.example.weatherapp.data.dataSource.FakeRepo
import com.example.weatherapp.data.local.LocalInterface
import com.example.weatherapp.data.network.GetFromApi
import com.example.weatherapp.favoriteScreen.viewModel.FavoriteViewModel
import com.example.weatherapp.getOrAwaitValue
import com.example.weatherapp.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
@ExperimentalCoroutinesApi

class AlertViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatchers()

    private lateinit var alertViewModel: AlertViewModel
    private lateinit var fakeRepository: FakeRepo

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var weatherResponse: WeatherResponse
//    lateinit var favoriteList: MutableList<Favourite>
//    lateinit var alertList: MutableList<AlertModel>


    private lateinit var remoteDataSource: GetFromApi
    private lateinit var localDataSource: LocalInterface
    private lateinit var repository: Repository
    lateinit var current: AlertModel


    @Before
    fun setUp(){
        weatherResponse = WeatherResponse(
            current = null,
            lat = null,
            lon = null,
            timezone_offset = null,
            timezone = null,
            hourly = emptyList(),
            daily = emptyList()

        )
        current = AlertModel(startTime = 1, endTime = 3)


        remoteDataSource = FakeRemoteDataSource()
        localDataSource = FakeLocalDataSource()
        repository = Repository(remoteDataSource ,localDataSource)
        fakeRepository = FakeRepo()
       alertViewModel = AlertViewModel(fakeRepository)
    }
    @Test
    fun insertAlert_andCheckTheListIsNotEmpty() = mainDispatcherRule.runBlockingTest{
        alertViewModel.insertAlert(current)
        var result = alertViewModel._alert.getOrAwaitValue {  } as AlertRoomState.Success
        assertThat(result.data, CoreMatchers.`is`(CoreMatchers.not(CoreMatchers.nullValue())))

    }
    @Test
    fun deleteAlert_andCheckIsDeleted() =mainDispatcherRule.runBlockingTest {
        alertViewModel.insertAlert(current)
        alertViewModel.deleteAlert(current)
        var result = alertViewModel._alert.getOrAwaitValue {  } as AlertRoomState.Success
        assertThat(result.data, CoreMatchers.`is`(emptyList()))
    }
    @Test
    fun getAlert_andChecked_ListIsNotEmpty() = mainDispatcherRule.runBlockingTest {
        alertViewModel.insertAlert(current)
        alertViewModel.getAlert()
        var result = alertViewModel._alert.getOrAwaitValue {  } as AlertRoomState.Success
        assertThat(result.data, CoreMatchers.`is`(CoreMatchers.not(emptyList())))
    }

}