package com.example.weatherapp.model

import retrofit2.Response

sealed class RoomState{
    class Success (val data: List<Favourite>):RoomState()
    class Failure(val msg:Throwable):RoomState()
    object Loading:RoomState()
}

