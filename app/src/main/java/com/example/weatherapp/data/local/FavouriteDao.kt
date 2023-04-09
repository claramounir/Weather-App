package com.example.weatherapp.data.local

import androidx.room.*
import com.example.weatherapp.model.Favourite
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite_table")

   fun getFavorites(): Flow<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorite(fav: Favourite)

    @Delete
    suspend fun deleteFromFavorite(fav: Favourite): Int





//    fun getFavorites(): Flow<List<Favourite>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFavorite(favourite: Favourite)
//
//    @Delete
//    suspend fun deleteFavorite(favourite: Favourite)

}