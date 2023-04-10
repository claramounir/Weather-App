package com.example.weatherapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class ConcreteLocalSourceTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var localSource: ConcreteLocalSource
lateinit var alert:AlertModel
lateinit var favourite: Favourite
@Before
fun setUp(){
    alert = AlertModel(startTime = 1, endTime = 4)
    favourite = Favourite(latitude = 23.4, longitude = 78.9,city = "Alexandria")
    localSource = ConcreteLocalSource.getInstance(
        ApplicationProvider.getApplicationContext()
    )
}

    @Test
    fun insertFav_AndChechedIsExist() = runBlockingTest{
        localSource.insertToAlert(alert)
        var final = localSource.getAlert().getOrAwaitValue {  }
        Assertions.assertThat(final).contains(alert)




    }


}