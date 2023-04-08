package com.example.weatherapp.favoriteScreen.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.favoriteScreen.viewModel.FavoriteViewModel
import com.example.weatherapp.favoriteScreen.viewModel.FavoriteViewModelFactory
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.Repository


class FavoriteFragment : Fragment(),OnFavClickListener {
lateinit var myViewModel : FavoriteViewModel
lateinit var myViewModelFactory: FavoriteViewModelFactory
lateinit var favAdapter:FavoriteAdapter
private lateinit var binding:FragmentFavoriteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container, false)
        val myRoot: View = binding.root

        // fav factory
        myViewModelFactory = FavoriteViewModelFactory(
            Repository.getInstance(
                ApiResponse.getINSTANCE(),ConcreteLocalSource.getInstance(requireContext())
            )
        )
        // viewmodel
        myViewModel = ViewModelProvider(requireActivity(),myViewModelFactory)[FavoriteViewModel::class.java]
        favAdapter = FavoriteAdapter(listOf(),this)

        // observation
        myViewModel.favWeather.observe(viewLifecycleOwner){
            favAdapter.setList(it)
            binding.recyclRv.adapter = favAdapter
            binding.recyclRv.layoutManager = LinearLayoutManager(context)

        }
        return myRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtn.setOnClickListener{

            val action=FavoriteFragmentDirections.actionFavoriteFragmentToMapsFragment(false,false,1)
            findNavController().navigate(action)

        }
    }


    override fun sendWeather(lat: Double, lon: Double) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(lat.toFloat(),lon.toFloat())
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun deleteWeather(fav: Favourite) {
      myViewModel.deleteFavWeather(fav)
    }
}