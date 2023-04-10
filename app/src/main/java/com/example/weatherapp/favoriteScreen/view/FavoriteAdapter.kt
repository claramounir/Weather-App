package com.example.weatherapp.favoriteScreen.view

import android.content.Context
import android.location.Geocoder
import android.os.RemoteException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.databinding.RowFavBinding
import com.example.weatherapp.homeScreen.view.DayAdapter
import com.example.weatherapp.model.Favourite
import java.io.IOException
import java.util.*

class FavoriteAdapter(private var list:List<Favourite>, val onClick:OnFavClickListener ): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
        lateinit var context: Context
        lateinit var binding: RowFavBinding


    class ViewHolder(var binding: RowFavBinding):RecyclerView.ViewHolder(binding.root) {}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowFavBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size

    }
    fun setList(fav: List<Favourite>){
        list = fav
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myObj = list.get(position)
        val lat = myObj.latitude
        val lon = myObj.longitude
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(lat,lon,1)
            if (addressList == null || addressList.isEmpty()) {
                holder.binding.countryNameFav.text = myObj.city
            } else {
                val address = addressList[0]

                holder.binding.countryNameFav.text =

                address.adminArea + " - " + address.countryName

            }

        } catch (e: IOException) {
            holder.binding.countryNameFav.text = myObj.city
        }catch (e: RemoteException) {
            holder.binding.countryNameFav.text = myObj.city
        }

//        holder.binding.countryTemp.text = myObj.
        holder.binding.deleteBtn.setOnClickListener {
                onClick.deleteWeather(myObj)
            notifyDataSetChanged()

        }


        holder.binding.cardFav.setOnClickListener {
            if (lon != null) {
                if (lat != null) {

                    onClick.sendWeather(lat,lon)
                }
            }
        }



    }

}