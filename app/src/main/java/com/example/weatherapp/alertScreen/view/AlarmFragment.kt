package com.example.weatherapp.alertScreen.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.view.isVisible
import com.example.weatherapp.R
import com.example.weatherapp.alertScreen.view.AlertAdapter
import com.example.weatherapp.databinding.FragmentAlarmBinding
import com.example.weatherapp.databinding.FragmentAlertBinding
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class AlarmFragment : Fragment() {

    private lateinit var binding: FragmentAlarmBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val myRoot: View = binding.root

        return myRoot
    }

}