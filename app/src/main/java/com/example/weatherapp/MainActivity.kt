package com.example.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.homeScreen.view.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation, navController)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
//                   HomeFragment()
                   loadFragment( HomeFragment())
                   true
                }
                R.id.nav_fav -> {
//                    FavoriteFragment
                    loadFragment(FavoriteFragment())
                    true
                }
                R.id.nav_alert -> {
                    loadFragment(AlertFragment())
                    true
                }
                R.id.nav_account -> {
                    loadFragment(SettingFragment())
                    true
                }
            }
            false
        }

//        val toolbar = getSupportActionBar();
//
//        loadFragment(HomeFragment.newInstance())
//
//        BottomNavigationView.setOnItemSelectedListener { item ->
//            var fragment: Fragment
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    toolbar?.setTitle("Home")
//                    fragment = HomeFragment()
//                    loadFragment(fragment)
//                    true
//                }
//                R.id.nav_account -> {
//                    toolbar?.setTitle("Radio")
//                    fragment = SettingFragment()
//                    loadFragment(fragment)
//                    true
//
//                }
//                R.id.nav_alert -> {
//                    toolbar?.setTitle("Search")
//                    fragment = AlertFragment()
//                    loadFragment(fragment)
//                    true
//
//                }
//                R.id.nav_fav -> {
//                    toolbar?.setTitle("My Music")
//                    fragment = FavoriteFragment()
//                    loadFragment(fragment)
//                    true
//
//                }
//
//                else -> false
//            }
//
//        }
//
//        bottom_navigation.setOnItemReselectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    Toast.makeText(this, "Home Item reselected", Toast.LENGTH_SHORT).show()
//                }
//                R.id.nav_alert -> {
//                    Toast.makeText(this, "Alert Item reselected", Toast.LENGTH_SHORT).show()
//                }
//                R.id.nav_account -> {
//                    Toast.makeText(this, "SettingItem reselected", Toast.LENGTH_SHORT).show()
//                }
//                R.id.nav_fav -> {
//                    Toast.makeText(this, "Favorite Item reselected", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        }
//    }
//

//    }
    }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}