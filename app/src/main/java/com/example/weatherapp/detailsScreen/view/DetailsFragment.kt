package com.example.weatherapp.detailsScreen.view

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentDetailsBinding
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.detailsScreen.viewModel.DetailsViewModelFactory
import com.example.weatherapp.homeScreen.view.DayAdapter
import com.example.weatherapp.homeScreen.view.HourAdapter
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class DetailsFragment : Fragment() {

    lateinit var myViewModel: HomeViewModel
    lateinit var myViewModelFactory: DetailsViewModelFactory
    lateinit var progressDialog: DialogFragment
    private var _binding: FragmentDetailsBinding? = null
  lateinit var binding : FragmentDetailsBinding

    lateinit var geocoder: Geocoder
    private lateinit var fusedClient : FusedLocationProviderClient
    private var _data : MutableLiveData<LatLng> = MutableLiveData<LatLng>()
    val data: LiveData<LatLng> = _data



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//Location Gps
        geocoder = Geocoder(requireActivity(), Locale.getDefault())
        fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = _binding as FragmentDetailsBinding
        val root: View = binding.root

            myViewModelFactory = DetailsViewModelFactory(Repository.getInstance(ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(requireContext())))


        myViewModel =
            ViewModelProvider(this.requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
//        (myViewModel as HomeViewModel).getWeatherDetails(lat)
        (myViewModel as HomeViewModel).weatherDetails.observe(viewLifecycleOwner) {
            val adress = it.lat?.let { it1 -> it.lon?.let { it2 ->
                geocoder.getFromLocation(it1,
                    it2,1)

            }
            }

            _binding!!.countryTxt?.text = adress?.get(0)?.getAddressLine(0)!!.split(",")[1]
//            _binding?.countryTxt?.text = it.timezone
            _binding?.CelsusTxt?.text = it.current?.temp.toString() + Constant.CELSIUS
            val dayhome= it.current?.dt?.let { it1 -> getCurrentDay(it1.toInt()) }
            _binding?.homeDecs?.text = it.current?.weather?.get(0)?.description
            _binding?.dateTxt?.text= dayhome
            _binding?.pressureValueTxt?.text= it.current?.temp.toString() + Constant.CELSIUS
            binding.humidityValueTxt.text = it.current?.humidity.toString() + "%"

            Glide.with(requireActivity()).load("https://openweathermap.org/img/wn/${it.current?.weather?.get(0)?.icon}@2x.png").into(binding.imgMain)

            _binding?.windValueTxt?.text = it.current?.windSpeed.toString() + Constant.WINDSPEED

            _binding?.cloudValueTxt?.text = it.current?.pressure.toString() + Constant.MBAR
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
//
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
//        return inflater.inflate(R.layout.fragment_details, container, false)
//    }


}