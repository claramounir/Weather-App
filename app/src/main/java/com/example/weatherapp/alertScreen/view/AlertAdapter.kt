package com.example.weatherapp.alertScreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.alertScreen.OnAlertClickListener
import com.example.weatherapp.databinding.RowAlertBinding
import com.example.weatherapp.model.AlertModel


 class AlertAdapter(private var list:List<AlertModel>, private val onClick: OnAlertClickListener): RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var binding: RowAlertBinding

    class ViewHolder(var binding: RowAlertBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowAlertBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun setList(alert: List<AlertModel>) {
        list = alert
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myObj = list.get(position)

        holder.binding.from.text = myObj.startTime.toString()
        holder.binding.toTxt.text = myObj.endTime.toString()
        holder.binding.colonTxt.text = " : "
        holder.binding.deleteBtn.setOnClickListener {
            onClick.deleteAlert(myObj)
            notifyDataSetChanged()

        }
    }
}