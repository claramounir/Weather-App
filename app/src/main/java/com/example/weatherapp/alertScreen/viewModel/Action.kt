package com.example.weatherapp.alertScreen.viewModel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.Constant


class Action : BroadcastReceiver() {
    lateinit var alarm :Ringtone
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent?) {
       val notification = NotificationClass(context)
        if (intent?.action.equals(Constant.ACTION_SNOOZE)){
            alarm.stop()
            notification.alarmNotificationManager(context).cancel(0)
        }
    }
}