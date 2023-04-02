package com.example.weatherapp.favoriteScreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RowFavBinding
import com.example.weatherapp.homeScreen.view.DayAdapter
import com.example.weatherapp.model.Favourite

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

        holder.binding.countryNameFav.text = myObj.city
        holder.binding.countryTemp.text = myObj.city
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