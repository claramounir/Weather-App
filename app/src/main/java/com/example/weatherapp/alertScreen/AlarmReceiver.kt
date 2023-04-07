package com.example.weatherapp.alertScreen

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.work.WorkManager
import com.example.weatherapp.Constant
import com.example.weatherapp.Utils
import com.example.weatherapp.alertScreen.viewModel.NotificationClass


import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Repository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager
    var notificationId: Int? = null
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        lateinit var notif: Uri
        lateinit var alarm: Ringtone

    }

    override fun onReceive(context: Context, intent: Intent) {
        sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE)
        val repo = Repository.getInstance(ApiResponse.getINSTANCE(), ConcreteLocalSource(context))
        PreferenceManager.getDefaultSharedPreferences(context)
        var alertJson = intent.getStringExtra(Constant.Alert)

        var alert = Gson().fromJson(alertJson, AlertModel::class.java)
        val notification = NotificationClass(context)
        notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationManager = notification.alarmNotificationManager(context)
        }


        Log.e("onReceive", "koki")
        Toast.makeText(context, "OnReceive alarm", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
           Utils.isDaily(alert.startTime, alert.endTime)
                Utils.canelAlarm(context, alert.toString(), alert.startTime.toInt())
                repo.deleteFromAlert(alert)
              WorkManager.getInstance(context.applicationContext).cancelAllWorkByTag(alert.startTime.toString())

            alert.latitude?.let { alert.longitude?.let { it1 -> repo.getWeatherFromApi(lat = it, lon = it1, "exclude","a62af663ada4f8dbf13318c557451a3b") } }
           notif = RingtoneManager.getActualDefaultRingtoneUri(
                context.applicationContext,
                RingtoneManager.TYPE_ALARM
            )
            alarm = RingtoneManager.getRingtone(context.applicationContext, notif)

        }

    }
}