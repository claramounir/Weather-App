package com.example.weatherapp.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.data.dataSource.FakeLocalDataSource
import com.example.weatherapp.data.dataSource.FakeRemoteDataSource
import com.example.weatherapp.data.local.LocalInterface
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.data.network.GetFromApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class RepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    private lateinit var remoteDataSource: GetFromApi
    private lateinit var localDataSource: LocalInterface
    private lateinit var repository: Repository


lateinit var weatherResponse: WeatherResponse
lateinit var favoriteList: MutableList<Favourite>
lateinit var alertList: MutableList<AlertModel>
@Before
fun getRepo(){
    weatherResponse = WeatherResponse(
        current = null,
        lat = null,
        lon = null,
        timezone_offset = null,
        timezone = null,
        hourly = emptyList(),
        daily = emptyList()

    )
  favoriteList = mutableListOf<Favourite>(
        Favourite(longitude = 56.9, latitude = 58.9, city = "Alexandria")

    )
   alertList  = mutableListOf<AlertModel>(
        AlertModel(startTime = 2, endTime = 5)
    )
    remoteDataSource = FakeRemoteDataSource()
    localDataSource = FakeLocalDataSource()
    repository = Repository(remoteDataSource ,localDataSource)
}
  @Test
  fun testRetrofit() = runBlocking{
      val test = weatherResponse
      repository.getWeatherFromApi(lat = 34.4, lon = 36.9, exclude = "ex", appid = "a62af663ada4f8dbf13318c557451a3b")
          .collect{
              assertEquals(test,it?.body())
          }

  }

    @Test
    fun insertToFavorite() = runBlocking{
val item = favoriteList.get(0)
        repository.insertToFavorite(item)
        val result = repository.getFavorites().collect{
            assertEquals(it.get(0).city,item.city)
        }

    }

    @Test
    fun deleteFromFavorite() = runBlocking {
        val item = favoriteList.get(0)
        repository.insertToFavorite(item)
        repository.deleteFromFavorite(item)
        val result = repository.getFavorites().collect{
      assertThat(it,`is`(listOf()))

        }
    }

    @Test
    fun getFavorites() = runBlocking{
        val item = favoriteList.get(0)
        repository.insertToFavorite(item)
        repository.insertToFavorite(item)
       repository.getFavorites().collect{
    assertThat(it,`is`(listOf(item,item)))
}
    }

}