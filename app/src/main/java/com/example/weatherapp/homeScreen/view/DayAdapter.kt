package com.example.weatherapp.homeScreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RowDaysBinding
import com.example.weatherapp.model.Daily
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(private val fragment: Fragment) : RecyclerView.Adapter<DayAdapter.ViewHolder>(){
    lateinit var context: Context
    lateinit var clara: RowDaysBinding
    private var current = emptyList<Daily>()
    fun setWeatherDay(weatherDays: List<Daily>) {
        this.current = weatherDays
        notifyDataSetChanged()
    }

    class ViewHolder (var binding :RowDaysBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       clara= RowDaysBinding.inflate(inflater,parent,false)
        return ViewHolder(clara)
    }

    override fun getItemCount(): Int {
        return current.size    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentObj = current.get(position)
        Glide.with(context).load("https://openweathermap.org/img/wn/${currentObj.weather.get(0).icon}@2x.png").into(holder.binding.dayImg)
        val max = currentObj.temp?.max?.let { Math.ceil(it).toInt() }
        val min = currentObj.temp?.min?.let { Math.ceil(it).toInt() }
        holder.binding.degreeTxt.text="$max/$min°C"
        val date= Date(currentObj.dt*1000L)
        val sdf= SimpleDateFormat("d")
        sdf.timeZone= TimeZone.getDefault()
        val formatedData=sdf.format(date)
        val calendar=Calendar.getInstance()
        val intDay=formatedData.toInt()
        calendar.set(Calendar.DAY_OF_MONTH,intDay)
        val format=SimpleDateFormat(/* pattern = */ "EEEE")
        val day=format.format(calendar.time)
        holder.binding.dayTxt.text=day
        holder.binding.skyTxt.text= currentObj.weather.get(0).description    }
}