package com.example.weatherapp.homeScreen.view

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.Constant.MBAR
import com.example.weatherapp.homeScreen.viewModel.MyLocation
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var myViewModel: HomeViewModel
    lateinit var myViewModelFactory: HomeViewModelFactory
    lateinit var progressDialog: DialogFragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var geocoder: Geocoder
    private lateinit var fusedClient : FusedLocationProviderClient



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
   var gps= MyLocation(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        geocoder = Geocoder(requireActivity(), Locale.getDefault())


        myViewModelFactory = HomeViewModelFactory(Repository.getInstance(ApiResponse.getINSTANCE()),requireContext(),gps)

        myViewModel =
            ViewModelProvider(this.requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        myViewModel.getLocation(requireContext())
//        (myViewModel as HomeViewModel).getWeatherDetails(lat)
        (myViewModel as HomeViewModel)._weatherDetails.observe(viewLifecycleOwner) {
         val adress = it.lat?.let { it1 -> it.lon?.let { it2 ->
             geocoder.getFromLocation(it1,
                 it2,1)
         } }
            _binding!!.countryTxt?.text = adress?.get(0)?.adminArea+"-"+adress?.get(0)?.countryName
//            _binding?.countryTxt?.text = it.timezone
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
        fun getCurrentDay( dt: Int) : String{
        var date= Date(dt*1000L)
        var sdf= SimpleDateFormat("d")
        sdf.timeZone= TimeZone.getDefault()
        var formatedData=sdf.format(date)
        var intDay=formatedData.toInt()
        var calendar= Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,intDay)
        var format= SimpleDateFormat("EEEE")
        return format.format(calendar.time)
    }
    fun formatTime(dateObject: Long?): String {
        val date = Date(dateObject!! * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}