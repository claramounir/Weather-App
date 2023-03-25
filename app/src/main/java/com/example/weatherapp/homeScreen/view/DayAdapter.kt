package com.example.weatherapp.homeScreen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.RowDaysBinding
import com.example.weatherapp.model.Daily

class DayAdapter(private val fragment: Fragment) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private var days = emptyList<Daily>()
    fun setWeatherDay(weatherDays: List<Daily>) {
        this.days = weatherDays
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: RowDaysBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( RowDaysBinding.inflate( LayoutInflater.from(parent.context), parent, false))

    }


    override fun getItemCount(): Int {
      return days.size-1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}