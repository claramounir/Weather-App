package com.example.weatherapp.alertScreen.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.weatherapp.Constant
import com.example.weatherapp.Utils
import com.example.weatherapp.alertScreen.AlarmReceiver
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Repository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
  CoroutineWorker(appContext, workerParams) {



    override  suspend fun doWork(): Result {

        var repository= Repository.getInstance(ApiResponse.getINSTANCE(),ConcreteLocalSource.getInstance(applicationContext))
        val alertJson = inputData.getString(Constant.Alert)
        var alert = Gson().fromJson(alertJson, AlertModel::class.java)
        if(alert.endTime in alert.startTime ..alert.endTime)
        {

                setAlarm(alert.startTime,alertJson,alert.startTime.toInt())
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "daily worker", Toast.LENGTH_SHORT).show()
                }

        }

        if(alert.endTime<System.currentTimeMillis())
        {
            WorkManager.getInstance(applicationContext).cancelAllWorkByTag(alert.startTime.toString())
            repository.deleteFromAlert(alert)
            Utils.canelAlarm(applicationContext, alert.toString(),alert.startTime.toInt())
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "your worker ended", Toast.LENGTH_SHORT).show()
            }
                }

        return Result.success()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(dateInMillis: Long,alert:String?,requestCode:Int) {
         var alarmMgr: AlarmManager? = null
         lateinit var alarmIntent: PendingIntent
        alarmMgr = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(applicationContext, AlarmReceiver::class.java).putExtra(Constant.Alert,alert).let { intent ->
            PendingIntent.getBroadcast(applicationContext, requestCode, intent, FLAG_IMMUTABLE)
        }
        alarmMgr?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            alarmIntent
        )

    }

}