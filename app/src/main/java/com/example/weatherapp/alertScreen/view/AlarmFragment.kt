package com.example.weatherapp.alertScreen.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import com.example.weatherapp.Constant
import com.example.weatherapp.Constant.Alert
import com.example.weatherapp.R
import com.example.weatherapp.Utils
import com.example.weatherapp.Utils.getCurrentDate
import com.example.weatherapp.Utils.getCurrentTime
import com.example.weatherapp.alertScreen.view.AlertAdapter
import com.example.weatherapp.alertScreen.viewModel.AlertViewModel
import com.example.weatherapp.alertScreen.viewModel.AlertViewModelFactory
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentAlarmBinding
import com.example.weatherapp.databinding.FragmentAlertBinding
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.AlertSettings
import com.example.weatherapp.model.Repository
import com.example.weatherforecast.model.SharedPrefrences.SharedManger
import com.google.gson.Gson
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MONTHS

private const val TAGA = "AlarmFragment"

class AlarmFragment : Fragment() {

    private lateinit var _binding: FragmentAlarmBinding
    private val binding get() = _binding!!
    lateinit var timePicker: TimePicker
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendarStart: Calendar
    lateinit var calenderEnd: Calendar
    lateinit var myViewModel: AlertViewModel

    val RQS_1 = 1

    companion object {
        const val TAG = "AlarmFragment"
    }

    override fun onStart() {
        super.onStart()
//        val window: Window? = dialog!!.window
//        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val root: View = binding.root


        SharedManger.init(requireContext())
//        var repository =
//            Repository.getInstance(LocalDataSource(requireContext()), RemoteDataSource())
        var repository = Repository.getInstance(
            ApiResponse.getINSTANCE(),
            ConcreteLocalSource.getInstance(requireContext())
        )

        val myViewModel =
            ViewModelProvider(this, AlertViewModelFactory(repository)).get(
                AlertViewModel::class.java
            )
        var alertSettings = myViewModel.getAlertSettings()
        calendarStart = Calendar.getInstance()
        calenderEnd = Calendar.getInstance()
        binding.tvFromDate.text = getCurrentDate()
        binding.tvToDate.text = getCurrentDate()
        binding.tvFromTimePicker.text = getCurrentTime()
        binding.tvToTimePicker.text = getCurrentTime()
        if (Utils.isOnline(requireContext())) {
            binding.btnMap.isEnabled = true
            binding.btnSet.isEnabled = true
        } else {


            binding.btnMap.isEnabled = false
            binding.btnSet.isEnabled = false
            Toast.makeText(
                requireContext(),
                "Please enable your network connection ",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (alertSettings?.isALarm == true && alertSettings.isNotification == false) {

            binding.radioButtonAlarm.isChecked = true
        }
        if (alertSettings?.isALarm == false && alertSettings.isNotification) {

            binding.radioButtonNotify.isChecked = true
        }
        if (alertSettings?.isALarm == false && alertSettings.isNotification) {
            binding.radioButtonNotify.isChecked = true
        }
        binding.tvFromTimePicker.setOnClickListener {
            pickDateTime(binding.tvFromDate, binding.tvFromTimePicker, calendarStart)
        }
        binding.tvToTimePicker.setOnClickListener {

            pickDateTime(binding.tvToDate, binding.tvToTimePicker, calenderEnd)
        }

        binding.btnMap.text =
            Utils.getAddressEnglish(requireContext(), alertSettings?.lat, alertSettings?.lon)
        binding.btnMap.setOnClickListener {
            val action =
                AlertFragmentDirections.actionAlertFragmentToAlarmFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.btnSet.setOnClickListener {

            Log.e(
                TAGA, "onCreateView: start Date " +
                        Utils.formatDateAlert(calendarStart.timeInMillis) + " start time " + Utils.formatTimeAlert(
                    calendarStart.timeInMillis
                ) +
                        " end date " + Utils.formatDateAlert(calenderEnd.timeInMillis) + " end time " + Utils.formatTimeAlert(
                    calenderEnd.timeInMillis
                )
            )
            var alert = AlertModel(
                startTime = calendarStart.timeInMillis,
                endTime = calenderEnd.timeInMillis,
                latitude = alertSettings!!.lat,
                longitude = alertSettings.lon,
                cityName = Utils.getAddressEnglish(
                    requireContext(),
                    alertSettings!!.lat,
                    alertSettings.lon
                )
            )
            if (alert.startTime < alert.endTime) {
                if (binding.radioButtonAlarm.isChecked) {
                    alertSettings?.isALarm = true
                    alertSettings?.isNotification = false
                }
                if (binding.radioButtonNotify.isChecked) {
                    alertSettings?.isALarm = false
                    alertSettings?.isNotification = true
                }
                myViewModel.saveAlertSettings(alertSettings)
                myViewModel.insertAlert(alert)
                Log.e(TAGA, "onCreateView: " + myViewModel.getAlertSettings().toString())

                val inputData = Data.Builder()
                inputData.putString(Constant.Alert, Gson().toJson(alert).toString())

                // Create a Constraints that defines when the task should run
                val myConstraints: Constraints = Constraints.Builder()
                    .setRequiresDeviceIdle(false)
                    .setRequiresCharging(false)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                Toast.makeText(
                    context,
                    Utils.formatTimeAlert(alert.startTime) + " " + Utils.formatTimeAlert(alert.endTime),
                    Toast.LENGTH_SHORT
                ).show()

                Toast.makeText(context, "Daily", Toast.LENGTH_SHORT).show()

                val myWorkRequest =
                    PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.DAYS).setConstraints(
                        myConstraints
                    ).setInputData(inputData.build()).addTag(alert.startTime.toString()).build()
                WorkManager.getInstance(requireContext().applicationContext)
                    .enqueueUniquePeriodicWork(
                        alert.startTime.toString(),
                        ExistingPeriodicWorkPolicy.REPLACE,
                        myWorkRequest
                    )

                val action =
                    AlarmFragmentDirections.actionAlarmFragmentToAlertFragment()
                NavHostFragment.findNavController(this).navigate(action)
            } else {
                Toast.makeText(
                    context,
                    "Please specify the end time of your alert",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        binding.btnCancel.setOnClickListener {
            val action =
                AlarmFragmentDirections.actionAlarmFragmentToAlertFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }


        return root

    }

    private fun pickDateTime(tvdate: TextView, tvTime: TextView, calendar: Calendar) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        var pickedDateTime: Calendar
        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(year, month, day, hour, minute)
                        tvdate.text = Utils.pickedDateFormatDate(pickedDateTime.time)
                        tvTime.text = Utils.pickedDateFormatTime(pickedDateTime.time)
                        calendar.time = pickedDateTime.time

                    },
                    startHour,
                    startMinute,
                    false
                ).show()
            },
            startYear,
            startMonth,
            startDay
        ).show()
    }

}

