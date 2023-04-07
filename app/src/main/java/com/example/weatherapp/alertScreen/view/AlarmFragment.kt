package com.example.weatherapp.alertScreen.view

import android.annotation.SuppressLint
import android.app.TimePickerDialog

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
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
import com.example.weatherapp.model.Repository
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

private const val TAGA = "AlarmFragment"

class AlarmFragment : Fragment() {

//    private lateinit var _binding: FragmentAlarmBinding
//    private val binding get() = _binding!!
//    lateinit var timePicker: TimePicker
//    lateinit var timePickerDialog: TimePickerDialog
//    lateinit var calendarStart: Calendar
//    lateinit var calenderEnd: Calendar
//
//    val RQS_1 = 1
//
//    companion object {
//        const val TAG = "AlarmFragment"
//    }
//
//    override fun onStart() {
//        super.onStart()
////        val window: Window? = dialog!!.window
////        window?.setBackgroundDrawableResource(android.R.color.transparent)
//    }

//    @SuppressLint("IdleBatteryChargingConstraints")
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
////        getDialog()?.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
////        setCancelable(false)
////        SharedManger.init(requireContext())
////        var repository =
////            Repository.getInstance(LocalDataSource(requireContext()), RemoteDataSource())
//        var repository= Repository.getInstance(ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(requireContext()))
//
//        val notificationsViewModel =
//            ViewModelProvider(this, AlertViewModelFactory(repository)).get(
//                AlertViewModel::class.java
//            )
////        var alertSettings = notificationsViewModel.getAlertSettings()
//        calendarStart = Calendar.getInstance()
//        calenderEnd = Calendar.getInstance()
//        binding.tvFromDate.text = getCurrentDate()
//        binding.tvToDate.text = getCurrentDate()
//        binding.tvFromTimePicker.text = getCurrentTime()
//        binding.tvToTimePicker.text = getCurrentTime()
//        if (Utils.isOnline(requireContext())) {
//            binding.btnMap.isEnabled = true
//            binding.btnSet.isEnabled = true
//        }
//        return
//    }
//
}