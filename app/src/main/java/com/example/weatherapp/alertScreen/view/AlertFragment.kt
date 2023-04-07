package com.example.weatherapp.alertScreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.alertScreen.OnAlertClickListener
import com.example.weatherapp.alertScreen.viewModel.AlertViewModel
import com.example.weatherapp.alertScreen.viewModel.AlertViewModelFactory
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentAlertBinding
import com.example.weatherapp.favoriteScreen.view.FavoriteFragmentDirections
import com.example.weatherapp.model.AlertModel
import com.example.weatherapp.model.Repository


class AlertFragment : Fragment(),OnAlertClickListener {
    lateinit var myViewModel : AlertViewModel
    lateinit var myViewModelFactory: AlertViewModelFactory
    lateinit var alertAdapter: AlertAdapter

    private lateinit var binding: FragmentAlertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
        binding = FragmentAlertBinding.inflate(inflater,container, false)
        val myRoot: View = binding.root

        // Inflate the layout for this fragment

        // alert factory
        myViewModelFactory = AlertViewModelFactory(
            Repository.getInstance(
                ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(requireContext())
            )
        )
        // viewmodel
        myViewModel = ViewModelProvider(requireActivity(),myViewModelFactory)[AlertViewModel::class.java]
        alertAdapter = AlertAdapter(listOf(),this)

        // observation
        myViewModel.favWeather.observe(viewLifecycleOwner){
            alertAdapter.setList(it)
            binding.recyclRv.adapter = alertAdapter
            binding.recyclRv.layoutManager = LinearLayoutManager(context)

        }
        return myRoot

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtn.setOnClickListener{

            val action= AlertFragmentDirections.actionAlertFragmentToAlarmFragment()
            findNavController().navigate(action)

        }
    }

    override fun deleteAlert(alert: AlertModel) {
        myViewModel.deleteAlert(alert)
    }

}