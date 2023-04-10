package com.example.weatherapp.favoriteScreen.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainDispatchers
import com.example.weatherapp.data.dataSource.FakeLocalDataSource
import com.example.weatherapp.data.dataSource.FakeRemoteDataSource
import com.example.weatherapp.data.dataSource.FakeRepo
import com.example.weatherapp.data.local.LocalInterface
import com.example.weatherapp.data.network.GetFromApi
import com.example.weatherapp.getOrAwaitValue
import com.example.weatherapp.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi

class FavoriteViewModelTest{


    @get:Rule
    var mainDispatcherRule = MainDispatchers()

    private lateinit var favViewModel: FavoriteViewModel
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
    lateinit var current: Favourite





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
        current = Favourite(latitude = 77.9, longitude = 78.0,"Egypt")


        remoteDataSource = FakeRemoteDataSource()
        localDataSource = FakeLocalDataSource()
        repository = Repository(remoteDataSource ,localDataSource)
        fakeRepository = FakeRepo()
        favViewModel = FavoriteViewModel(fakeRepository)
    }
    @Test
fun insertWeather_andCheckTheListIsNotEmpty() = mainDispatcherRule.runBlockingTest{
    favViewModel.insertFavWeather(current)
        var result = favViewModel._favWeather.getOrAwaitValue {  } as RoomState.Success
        assertThat(result.data,`is`(not(nullValue())))

}

    @Test
    fun deleteWeather_andCheckIsDeleted() =mainDispatcherRule.runBlockingTest {
        favViewModel.insertFavWeather(current)
        favViewModel.deleteFavWeather(current)
        var result = favViewModel._favWeather.getOrAwaitValue {  } as RoomState.Success
        assertThat(result.data,`is`(emptyList()))
    }

    @Test
    fun getWeather_andChecked_ListIsNotEmpty() = mainDispatcherRule.runBlockingTest {
        favViewModel.insertFavWeather(current)
        favViewModel.getWeather()
        var result = favViewModel._favWeather.getOrAwaitValue {  } as RoomState.Success
        assertThat(result.data,`is`(not(emptyList())))
    }
}