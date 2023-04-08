package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherforecast.model.SharedPrefrences.SharedManger
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    val navView: BottomNavigationView = binding.bottomNavigation

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation, navController)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.homeFragment || nd.id == R.id.favoriteFragment || nd.id == R.id.settingFragment
                || nd.id == R.id.alertFragment || nd.id == R.id.detailsFragment) {
              navView.visibility = View.VISIBLE
            } else {
        navView.visibility = View.GONE
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {

        SharedManger.init(newBase!!)
        if(SharedManger.getSettings()?.lang.equals(Constant.LANG_AR))
        {
            val lang_code = "ar" //load it from SharedPref
            val context: Context = Utils.changeLang(newBase!!, lang_code)!!

        }
        else
        {
            val lang_code = "en" //load it from SharedPref
            val context: Context = Utils.changeLang(newBase!!, lang_code)!!

        }

        super.attachBaseContext(newBase)
    }

}