package com.example.weatherapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.hamcrest.collection.IsEmptyCollection
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi

class FavouriteDaoTest {

    @get:Rule
    var instantExecutorRule= InstantTaskExecutorRule()
lateinit var  db: RoomDB
lateinit var dao: FavouriteDao
    @Before
    fun initDB() {
        //initialize database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDB::class.java
        ).allowMainThreadQueries().build()
       dao= db.getWeathersDao()
    }

    @After
    fun close() {
        // close database
        db.close()
    }

    @Test
    fun getFavorites_insertFavItems_itemsInsertSuccessfully() = runBlockingTest {

        // given
        val data = Favourite(
            latitude = 0.0,
        longitude = 0.0,
            city = "paris"
        )
        dao.insertToFavorite(data)
        val data2 = Favourite(
            latitude = 1.0,
            longitude = 1.0,
            city = "libya"
        )
        dao.insertToFavorite(data2)
        val data3 = Favourite(
            latitude = 2.0,
            longitude = 1.0,
            city = "moroco"
        )
        dao.insertToFavorite(data3)


        // when
        val results = dao.getFavorites().first()

        // then

        MatcherAssert.assertThat(results.size, Is.`is`(3) )


    }

    @Test
    fun insertToFavorite_insertSingleItem_returnItem() = runBlockingTest  {
        // given
        val data = Favourite(
            latitude = 0.0,
            longitude = 0.0,
            city = "paris"
        )
        //when
        dao.insertToFavorite(data)

        // then
        val results = dao.getFavorites().first()
        MatcherAssert.assertThat(results[0], IsNull.notNullValue() )


    }

    @Test
    fun deleteFromFavorite_deleteItem_checkIsNull() = runBlockingTest {
        //given
        val data = Favourite(
            latitude = 0.0,
            longitude = 0.0,
            city = "paris"
        )

        dao.insertToFavorite(data)
        // when
        dao.deleteFromFavorite(data)

        //then
        val results = dao.getFavorites().first()
        MatcherAssert.assertThat(results, IsEmptyCollection.empty())

    }
}