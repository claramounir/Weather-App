package com.example.weatherapp.alertScreen.viewModel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.Constant
import com.example.weatherapp.alertScreen.view.NotificationActivity
import com.example.weatherapp.R
import com.example.weatherapp.alertScreen.AlarmReceiver

class NotificationClass(context: Context) :ContextWrapper(context) {
    private val MY_CHANNEL = "my_channel"

    fun getNotificationBuilder(title: String?,body:String,context: Context,btmap:Bitmap ) :NotificationCompat.Builder
    {
        val contentIntent = PendingIntent.getActivity(context,0,Intent(context,AlarmReceiver::class.java),PendingIntent.FLAG_MUTABLE)

        return NotificationCompat.Builder(applicationContext, MY_CHANNEL)
//            .setSmallIcon(R.drawable.app_icon)
//            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setColor(ContextCompat.getColor(applicationContext, R.color.white))
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun alarmNotificationManager(context: Context): NotificationManager {
        val channel = NotificationChannel(
            MY_CHANNEL, resources.getString(R.string.notification),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description="description"

        }
        // for heads-up notifications

        // Register channel with system
        // Register channel with system
        val notificationManager: NotificationManager =
            context.getSystemService(NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
        return notificationManager
    }

fun getNotification(context: Context,notificationId: Int,title:String,description: String,bitmap:Bitmap): Notification {

    val notificationScreenIntent =
        Intent(context.applicationContext, NotificationActivity::class.java).apply {
            action= Constant.ACTION_SNOOZE
            putExtra(Constant.EXTRA_NOTIFICATION_ID,notificationId)
        }
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context.applicationContext, 0,
        notificationScreenIntent   , PendingIntent.FLAG_MUTABLE
    )
    val snoozeIntent = Intent(context, Action::class.java).apply {
        action = Constant.ACTION_SNOOZE
        putExtra(Constant.EXTRA_NOTIFICATION_ID, notificationId)

    }
    val snoozePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_MUTABLE)

    val notification: Notification =
        NotificationCompat.Builder(context,MY_CHANNEL )
            .setSmallIcon(R.drawable.cloud)
            .setContentTitle(title)
            .setContentText(description)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(Notification.CATEGORY_ALARM)
            .setLargeIcon(bitmap)
            .addAction(R.drawable.cloud, "Dismiss", snoozePendingIntent)
            .setFullScreenIntent(fullScreenPendingIntent,true)
            .setOngoing(true)
            .build()
    return notification
}

}




