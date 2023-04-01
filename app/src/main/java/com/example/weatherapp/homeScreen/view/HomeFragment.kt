package com.example.weatherapp.homeScreen.view

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.Constant.MBAR
import com.example.weatherapp.R
import com.example.weatherapp.homeScreen.viewModel.MyLocation
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.*
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
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

    lateinit var weatherHourAdapter: HourAdapter
    lateinit var recyclerViewHour : RecyclerView
    lateinit var imageViewLottie : LottieAnimationView
    lateinit var weatherDayAdapter: DayAdapter
    lateinit var gps: MyLocation

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    gps= MyLocation(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        geocoder = Geocoder(requireActivity(), Locale.getDefault())
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

    // hours
    private fun bindHourlyWeather(hourlyList: List<Hourly>) {
        weatherHourAdapter.setWeatherHours(hourlyList)
    }


    private fun initHourRecycle() {
        weatherHourAdapter = HourAdapter()
        _binding?.hoursRv.apply {
            this?.adapter = weatherHourAdapter
            this?.layoutManager = LinearLayoutManager(
              requireContext(),
                RecyclerView.HORIZONTAL, false
            )
        }
    }

    private fun bindDailyWeather(dailyList: List<Daily>) {
        weatherDayAdapter.setWeatherDay(dailyList)
    }

    private fun intDayRecycle() {
        weatherDayAdapter = DayAdapter(requireParentFragment())
        _binding?.daysRv.apply {
            this?.adapter = weatherDayAdapter
            this?.layoutManager = LinearLayoutManager(
            requireContext(),
                RecyclerView.VERTICAL, false
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHourRecycle()
        intDayRecycle()

        // textView = view.findViewById(R.id.response)
        val repository = Repository(requireContext())
        val viewModelFactory = HomeViewModelFactory(repository = repository,context = requireContext(), gps = gps)
        myViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)


        val flag = initFavSharedPref(requireContext()).getInt(getString(R.string.FAV_FLAG), 0)
        if (flag == 1 &&
            Navigation.findNavController(requireView()).previousBackStackEntry?.destination?.id == R.id.favoriteFragment
        ) {

            //require get Data
            val lat = initFavSharedPref(requireContext()).getFloat(getString(R.string.LAT), 0f)
            val long = initFavSharedPref(requireContext()).getFloat(getString(R.string.LON), 0f)

            myViewModel.getWeatherDetails(
                lat.toDouble(),
                long.toDouble(),
                "",
                getCurrentLan(requireContext()),
                getCurrentUnit(requireContext()),

                )



            //then update value
            initFavSharedPref(requireContext()).edit().apply {
                putInt(getString(R.string.FAV_FLAG), 0)
                apply()
            }

        } else {
            this.gps.updateLocationFromGPS()
            if ((initSharedPref(requireContext()).getInt("B", 2) == 1)) {
                Debug.getLocation()
                initSharedPref(requireContext()).edit().apply {
                    putInt(getString(R.string.LOCATION), 3)
                    apply()
                }


            }

        }

        lifecycleScope.launch {
            myViewModel.weatherDetails.collect { state ->
                when (state) {
                    is ApiResponse.OnSucess -> {
                        Log.i("TAGClara", "onViewCreated: ${state.data.toString()}")
//                        myViewModel =
//                            ViewModelProvider(this.requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
//                        myViewModel.getLocation(requireContext())
////        (myViewModel as HomeViewModel).getWeatherDetails(lat)
//                        (myViewModel as HomeViewModel)._weatherDetails.observe(viewLifecycleOwner) {
//                            val adress = it.lat?.let { it1 -> it.lon?.let { it2 ->
//                                geocoder.getFromLocation(it1,
//                                    it2,1)
//
//                        _binding!!.countryTxt?.text = adress?.get(0)?.adminArea+"-"+adress?.get(0)?.countryName
////            _binding?.countryTxt?.text = it.timezone
//                        _binding?.CelsusTxt?.text = it.current?.temp.toString() + Constant.CELSIUS
//                        val dayhome= it.current?.dt?.let { it1 -> getCurrentDay(it1.toInt()) }
//                        _binding?.homeDecs?.text = it.current?.weather?.get(0)?.description
//                        _binding?.dateTxt?.text= dayhome
//                        _binding?.pressureValueTxt?.text= it.current?.temp.toString() +Constant.CELSIUS
//                        binding.humidityValueTxt.text = it.current?.humidity.toString() + "%"
//
//                        Glide.with(requireActivity()).load("https://openweathermap.org/img/wn/${it.current?.weather?.get(0)?.icon}@2x.png").into(binding.imgMain)
//
//                        _binding?.windValueTxt?.text = it.current?.windSpeed.toString() + Constant.WINDSPEED
//
//                        _binding?.cloudValueTxt?.text = it.current?.pressure.toString() + MBAR
//                        var time= formatTime(it.current?.sunrise)
//                        var time2= formatTime(it.current?.sunset)
//                        _binding?.UVValueTxt?.text = time
//                        _binding?.visibilityValueTxt?.text = time2
//                        binding.daysRv.apply {
//                            layoutManager = LinearLayoutManager(context)
//                            this.adapter = DayAdapter(it.daily)
//                        }
//                        binding.hoursRv.apply {
//                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                            this.adapter = HourAdapter(it.hourly)
//
//                        }


                        // weather hour set
                        bindHourlyWeather(state.data.hourly)

                        // weather days set
                        bindDailyWeather(state.data.daily)

                    }
                    is ApiResponse.onError -> {}
                    is ApiResponse.OnLoading -> {}
                }

            }
        }




    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}