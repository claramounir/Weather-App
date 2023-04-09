package com.example.weatherapp.model

sealed class AlertRoomState{

    class Success (val data: List<AlertModel>):AlertRoomState()
    class Failure(val msg:Throwable):AlertRoomState()
    object Loading:AlertRoomState()
}
