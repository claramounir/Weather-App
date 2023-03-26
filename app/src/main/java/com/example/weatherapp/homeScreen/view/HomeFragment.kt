package com.example.weatherapp.homeScreen.view

import RoomDB
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.Constant.MBAR
import com.example.weatherapp.R
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.Repository
import com.google.android.gms.common.api.Api
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var myViewModel: ViewModel
    lateinit var myViewModelFactory: HomeViewModelFactory
    lateinit var progressDialog: DialogFragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        myViewModelFactory = HomeViewModelFactory(Repository.getInstance(ApiResponse.getINSTANCE()))

        myViewModel =
            ViewModelProvider(this.requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        (myViewModel as HomeViewModel).getWeatherDetails()
        (myViewModel as HomeViewModel)._weatherDetails.observe(viewLifecycleOwner) {
            _binding?.countryTxt?.text = it.timezone
            _binding?.CelsusTxt?.text = it.current?.temp.toString() + Constant.CELSIUS
            val dayhome= it.current?.dt?.let { it1 -> getCurrentDay(it1.toInt()) }
            _binding?.homeDecs?.text = it.current?.weather?.get(0)?.description
            _binding?.dateTxt?.text= dayhome
            _binding?.pressureValueTxt?.text= it.current?.temp.toString() +Constant.CELSIUS
            binding.humidityValueTxt.text = it.current?.humidity.toString() + "%"

            Glide.with(requireActivity()).load("https://openweathermap.org/img/wn/${it.current?.weather?.get(0)?.icon}@2x.png").into(binding.imgMain)

            _binding?.windValueTxt?.text = it.current?.windSpeed.toString() + Constant.WINDSPEED

            _binding?.cloudValueTxt?.text = it.current?.pressure.toString() + MBAR
            var time= formatTime(it.current?.sunrise)
            var time2= formatTime(it.current?.sunset)
            _binding?.UVValueTxt?.text = time
            _binding?.visibilityValueTxt?.text = time2
            binding.daysRv.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = DayAdapter(it.daily)
            }
            binding.hoursRv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this.adapter = HourAdapter(it.hourly)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getCurrentDay( dt: Int) : String{
        var date= Date(dt*1000L)
        var sdf= SimpleDateFormat("d")
        sdf.timeZone= TimeZone.getDefault()
        var formatedData=sdf.format(date)
        var intDay=formatedData.toInt()
        var calendar=Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,intDay)
        var format=SimpleDateFormat("EEEE")
        return format.format(calendar.time)
    }
    fun formatTime(dateObject: Long?): String {
        val date = Date(dateObject!! * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//    companion object {
//        fun newInstance() = HomeFragment()
//    }

}