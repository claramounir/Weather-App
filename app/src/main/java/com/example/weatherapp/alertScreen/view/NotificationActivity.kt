package com.example.weatherapp.alertScreen.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.weatherapp.R
import com.example.weatherapp.alertScreen.viewModel.NotificationClass
import com.example.weatherapp.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    var clara=com.example.weatherapp.alertScreen.AlarmReceiver.r

    private var _binding: ActivityNotificationBinding? = null
    private val binding get() = _binding
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        var myNotification= NotificationClass(applicationContext)
        binding?.btnDismiss?.setOnClickListener{
            clara.stop()
            myNotification.alarmNotificationManager(applicationContext).cancel(2)
            finish()
        }
    }
}