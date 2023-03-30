package com.example.weatherapp.favoriteScreen.view

import Favourite
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.RowFavBinding
import com.example.weatherapp.model.getCurrentLan
import com.example.weatherapp.model.initFavSharedPref
import java.util.*

class FavoriteAdapter (    private val fragment: Fragment,private val onDelete: (favourite: Favourite)
-> Unit): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    private var favWeather = emptyList<Favourite>()
    fun setFavWeather(favWeather: List<Favourite>) {
        this.favWeather = favWeather
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            RowFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        // change to city
        var cityName = getCityText(
            favWeather[position].latitude!!, favWeather[position].longitude!!, favWeather[position].city!!
        )

        holder.binding.countryNameFav.text = cityName

        // handle click
        holder.binding.countryTemp.setOnClickListener {
            // init shared

            initFavSharedPref(fragment.requireContext())
                .edit()
                .apply {
                    putFloat(fragment.getString(R.string.LON), favWeather[position].longitude!!.toFloat())
                    putFloat(fragment.getString(R.string.LAT), favWeather[position].latitude!!.toFloat())

                    putInt(fragment.getString(R.string.ID), favWeather[position].id!!)
                    putInt(fragment.getString(R.string.FAV_FLAG), 1)
                    apply()
                }

            // make some cond -- here i want to send lat and long .. then recive them at home
            Navigation.findNavController(fragment.requireView())
                .navigate(R.id.action_favoriteFragment_to_detailsFragment)
        }

        // handle delete
        holder.binding.deleteBtn.setOnClickListener {
            onDelete(favWeather[position])
        }
    }

    override fun getItemCount(): Int {
        return favWeather.size
    }

    inner class ViewHolder(
        val binding: RowFavBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    private fun getCityText(lat: Double, lon: Double, timezone: String): String {
        var city = "city"
        Locale.setDefault(Locale(getCurrentLan(fragment.requireContext()),"eg"))
        val new_locale = Locale.getDefault()
        val geocoder =
            Geocoder(fragment.requireContext(),new_locale)
        val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)!!
        if (addresses.isNotEmpty()) {
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            city = "$state, $country"
            if (state.isNullOrEmpty()) city = ""
        }
        if (city == "city")
            city = timezone
        return city
    }
}