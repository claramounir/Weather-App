package com.example.weatherapp.alertScreen

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weatherapp.Constant
import com.example.weatherapp.Utils
import com.example.weatherapp.alertScreen.viewModel.NotificationClass


import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Repository
import com.example.weatherforecast.model.SharedPrefrences.SharedManger
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    lateinit var notificationManager:NotificationManager
    var notificationId:Int?=null
    companion object{
        lateinit var notification:Uri
        lateinit var  r:Ringtone

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        SharedManger.init(context)
        var repo= Repository.getInstance( ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(context))
            var alertSettings=repo.getAlertSettings()
        var alertJson = intent.getStringExtra(Constant.Alert)
        var alert = Gson().fromJson(alertJson, AlertModel::class.java)
        val notificationHelper = NotificationClass(context)
        notificationId=1

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        }

        notificationManager=  notificationHelper.alarmNotificationManager(context)


        Log.e("onReceive", "ladskjflsakjdflskjdflskjdfslkjdflasdf")
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            if (!Utils.isDaily(alert.startTime,alert.endTime))
            {
                Utils.canelAlarm(context,alert.toString(),alert.startTime.toInt())
                repo.deleteFromAlert(alert)
                WorkManager.getInstance(context.applicationContext).cancelAllWorkByTag(alert.startTime.toString())
            }
            try {
                alert.longitude?.let {
                    alert.latitude?.let { it1 ->
                        repo.getWeatherFromApi(lat = it1, lon = it,"",Constant.appId)
                            .collectLatest {
                                val bitmap = arrayOf<Bitmap?>(null)

                                if (it != null) {
                                    Glide.with(context)
                                        .asBitmap()
                                        .load(it.body()?.current?.weather?.get(0)?.icon?.let { it2 ->
                                            Utils.getIconUrl(
                                                it2
                                            )
                                        })
                                        .into(object : CustomTarget<Bitmap?>() {
                                            @RequiresApi(Build.VERSION_CODES.S)
                                            override fun onResourceReady(
                                                resource: Bitmap,
                                                @Nullable transition: Transition<in Bitmap?>?
                                            ) {
                                                bitmap[0] = resource

                                                Log.e("onReceive", "onResourceReady: "+resource )



                                                notification = RingtoneManager.getActualDefaultRingtoneUri(
                                                    context.applicationContext,
                                                    RingtoneManager.TYPE_ALARM
                                                )
                                                //Uri.parse(("android.resource://" + context.applicationContext.packageName) + "/" + R.raw.weather_alarm)
                                                r = RingtoneManager.getRingtone(
                                                    context.applicationContext,
                                                    notification
                                                )
                                                if(alertSettings?.isALarm==true && !alertSettings.isNotification){
                                                    r.play()
                                                    notificationManager.notify(notificationId!!,
                                                        it.body()?.current?.weather?.get(0)?.description?.let { it2 ->
                                                            notificationHelper.getNotification(context,
                                                                notificationId!!, Utils.getAddressEnglish(context,alert.latitude,alert.longitude)!!,
                                                                it2,bitmap[0]!!)
                                                        })

                                                }
                                                if(alertSettings?.isALarm==false && alertSettings.isNotification){
                                                    notificationManager.notify(notificationId!!,
                                                        it.body()?.current?.weather?.get(0)?.description?.let { it2 ->
                                                            notificationHelper.getNotificationBuilder(
                                                                Utils.getAddressEnglish(context,alert.latitude,alert.longitude)!!,
                                                                it2,context,
                                                                bitmap[0]!!

                                                            ).build()
                                                        })
                                                }

                                            }

                                            override fun onLoadCleared(@Nullable placeholder: Drawable?) {

                                            }
                                        })
                                }


                            }
                    }
                }


            } finally {
                cancel()
            }

        }






    }

}