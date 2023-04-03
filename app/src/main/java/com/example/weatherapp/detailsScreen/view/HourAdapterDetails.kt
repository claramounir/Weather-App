package com.example.weatherapp.detailsScreen.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Constant
import com.example.weatherapp.databinding.RowTemperatureBinding
import com.example.weatherapp.model.Hourly
import java.text.SimpleDateFormat
import java.util.*

class HourAdapterDetails(var hourly: List<Hourly>) : RecyclerView.Adapter<HourAdapterDetails.ViewHolder>(){


    lateinit var context: Context
    lateinit var sara: RowTemperatureBinding
    class ViewHolder (var binding : RowTemperatureBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        sara=  RowTemperatureBinding.inflate(inflater,parent,false)
        return ViewHolder(sara)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentObj = hourly.get(position)
        var timeHour = getMyTime(currentObj.dt.toInt())
        Glide.with(context).load("https://openweathermap.org/img/wn/${currentObj.weather.get(0).icon}@2x.png").into(holder.binding.hourImg)
        holder.binding.CTxt.text = currentObj.temp.toString() + Constant.CELSIUS
        holder.binding.hourTxt.text= timeHour
    }
    @SuppressLint("SimpleDateFormat")
    fun getMyTime(dt: Int) : String{
        var date= Date(dt*1000L)
        var sdf= SimpleDateFormat("hh:mm a")
        sdf.timeZone= TimeZone.getDefault()
        return sdf.format(date)
    }


    override fun getItemCount(): Int {
        return hourly.size     }


}