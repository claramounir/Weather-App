package com.example.weatherapp.data.network

import RetrofitInstance
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.collection.IsEmptyCollection
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.create


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class ApiInterfaceTest {
    @get:Rule
    var instantExecuterRule = InstantTaskExecutorRule()
    var api:ApiInterface? = null

    @Before
    fun setUp() {
        api = RetrofitInstance.retrofitInstance.create()
    }

    @After
    fun tearDown() {
        api=null
    }

    @Test
    fun getWeatherDetalis()  = runBlocking{

        // given
        val latitude = 12.23
        val longitude = 23.54
        val lan = "en"
        val un = "c"
        val ex = "exclude"
        val appKey = "a62af663ada4f8dbf13318c557451a3b"

        // when
        val response = api?.getWeatherDetalis(
           lat = latitude,
            lon = longitude,
            language = lan,
             units = un,
            exclude = ex,
            appid = appKey

        )
        // then
        assertThat(response?.code() as Int,  Is.`is`(200))
        assertThat(response?.body() as WeatherResponse, IsNull.notNullValue())



    }
}