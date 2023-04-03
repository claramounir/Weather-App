package com.example.weatherapp.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.databinding.FragmentSplashBinding
import com.example.weatherapp.gpsMapDialog.AlertDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding:FragmentSplashBinding
    private val splashScreenScope = lifecycleScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container, false)
        val myRoot: View = binding.root
        return myRoot

        splashScreenScope.launch(Dispatchers.Default) {
            delay(2500)
            val intent = Intent(requireActivity(), AlertDialogFragment::class.java)
            startActivity(intent)
            onStop()
        }
    }

    override fun onPause() {
        super.onPause()
        splashScreenScope.cancel()
    }
}