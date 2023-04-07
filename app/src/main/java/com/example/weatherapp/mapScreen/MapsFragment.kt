package com.example.weatherapp.mapScreen

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.favoriteScreen.viewModel.FavoriteViewModel
import com.example.weatherapp.favoriteScreen.viewModel.FavoriteViewModelFactory
import com.example.weatherapp.homeScreen.viewModel.HomeViewModel
import com.example.weatherapp.homeScreen.viewModel.HomeViewModelFactory
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    val args:MapsFragmentArgs by navArgs()
lateinit var binding: FragmentMapsBinding
lateinit var fusedClient:FusedLocationProviderClient
lateinit var mapFragment: SupportMapFragment
lateinit var mMap: GoogleMap
var lat:Double = 0.0
  var long :Double = 0.0
    lateinit var mainActivity: MainActivity
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    var fav:Favourite = Favourite(0.0,0.0,"default")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.setOnMapClickListener {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            lat =it.latitude
            long=it.longitude
            goToLatLng(it.latitude,it.longitude,16f)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModelFactory = HomeViewModelFactory(Repository.getInstance(ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(requireContext())))
        homeViewModel =
            ViewModelProvider(this.requireActivity(), homeViewModelFactory)[HomeViewModel::class.java]
        favoriteViewModelFactory = FavoriteViewModelFactory(
            Repository.getInstance(
                ApiResponse.getINSTANCE(),ConcreteLocalSource.getInstance(requireContext())
            )
        )
        // viewmodel
        favoriteViewModel = ViewModelProvider(requireActivity(),favoriteViewModelFactory)[FavoriteViewModel::class.java]
        binding = FragmentMapsBinding.inflate(inflater,container,false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        mapInitialize()
        binding.saveBtn.setOnClickListener{
            Log.i("claramap","clara"+lat.toString()+long.toString())
            if (lat != null && long != null) {
            if (args.from == 1) {

                    homeViewModel.getWeatherDetails(
                        lat.toDouble(),
                        long.toDouble(),
                        "exclude",
                        "a62af663ada4f8dbf13318c557451a3b"
                    )
                    homeViewModel.weatherDetails.observe(viewLifecycleOwner) {

                        fav.latitude = it.lat!!
                        fav.longitude = it.lon!!
                        fav.city = it.timezone

                        Log.i("claramap","clara"+it.timezone)
                        favoriteViewModel.insertFavWeather(fav)
                        val action = MapsFragmentDirections.actionMapsFragmentToFavoriteFragment()
                        Navigation.findNavController(requireView()).navigate(action)
                    }
                } else {
                    val action = MapsFragmentDirections.actionMapsFragmentToHomeFragment(
                        lat.toFloat(),
                        long.toFloat()
                    )
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
        }
        return binding.root
    }

    private fun mapInitialize() {
        val locationRequest :LocationRequest = LocationRequest()
        locationRequest.setInterval(5000)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setSmallestDisplacement(14f)
        locationRequest.setFastestInterval(3000)
        binding.searchEdt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.action == KeyEvent.ACTION_DOWN
                || event.action == KeyEvent.KEYCODE_ENTER
            ) {
                if (!binding.searchEdt.text.isNullOrEmpty())
                  goToSearchLocation()
            }
            false
        }
        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        }

    private fun goToLatLng(latitude: Double,longitude:Double, float: Float) {
        var name = "Unknown "
        var geocoder = Geocoder(requireContext()).getFromLocation(latitude,longitude,1)
        if (geocoder!!.size>0)
            name = "${geocoder?.get(0)?.subAdminArea} , ${geocoder?.get(0)?.adminArea}"
        var latLng= LatLng(latitude,longitude)
        var update: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,float)
        mMap.addMarker(MarkerOptions().position(latLng))
        mMap.animateCamera(update)


    }
private fun goToSearchLocation(){
    var searchLocation = binding.searchEdt.text.toString()
    var list = Geocoder(requireContext()).getFromLocationName(searchLocation,1)
    if (list!= null && list.size>0){

        var address: Address = list.get(0)
        lat =address.latitude
        long=address.longitude
        goToLatLng(address.latitude,address.longitude,16f)
    }


}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}