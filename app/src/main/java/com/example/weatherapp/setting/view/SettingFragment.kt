package com.example.weatherapp.setting.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.Constant
import com.example.weatherapp.MainActivity
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.network.ApiResponse
import com.example.weatherapp.databinding.FragmentSettingBinding
import com.example.weatherapp.model.Repository
import com.example.weatherapp.model.Settings
import com.example.weatherapp.setting.viewModel.SettingsViewModel
import com.example.weatherapp.setting.viewModel.SettingsViewModelFactory
import com.example.weatherforecast.model.SharedPrefrences.SharedManger

private const val TAG = "SettingsFragment"

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private var settings: Settings? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SharedManger.init(requireContext())
       val repo =  Repository.getInstance(
            ApiResponse.getINSTANCE(), ConcreteLocalSource.getInstance(requireContext())
        )
        val settingsViewModel =
            ViewModelProvider(this, SettingsViewModelFactory(repo)).get(
                SettingsViewModel::class.java
            )
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        settings = settingsViewModel.getSettings()
        Log.i(TAG, "onCreateView: " + settings)
        initUi()

        binding.btnSaveSetting.setOnClickListener {
            savButtonCheck()
            Log.i(TAG, "onCreateView: " + settings)
            settingsViewModel.saveSettings(settings)
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            //  Navigation.findNavController(it).navigate(R.id.navigation_home)

        }
        binding.radioMap.setOnClickListener {
            val action=SettingFragmentDirections.actionSettingFragmentToMapsFragment(true,false,0)
            Navigation.findNavController(it).navigate(action)
        }
        // Inflate the layout for this fragment
//          return inflater.inflate(R.layout.fragment_setting, container, false)
        return root
    }

    private fun savButtonCheck() {
        if (binding.radioGps.isChecked) {
            settings?.isMap = false
        }
        if (binding.radioMap.isChecked) {
            settings?.isMap = true
        }
        if (binding.radioArabic.isChecked) {

            settings?.lang = Constant.LANG_AR
        }
        if (binding.radioEnglish.isChecked) {

            settings?.lang = Constant.LANG_EN
        }
        if (binding.kelvinBtn.isChecked) {
            settings?.unit = Constant.UNITS_DEFAULT
        }
        if (binding.fehrenBtn.isChecked) {
            settings?.unit = Constant.UNITS_FAHRENHEIT
        }
        if (binding.celsBtn.isChecked) {
            settings?.unit = Constant.UNITS_CELSIUS
        }

    }

    private fun initUi() {
        if (settings?.isMap == false) {
            binding.radioGps.isChecked = true
        }
        if (settings?.isMap == true) {
            binding.radioMap.isChecked = true
        }

        if (settings?.unit == Constant.UNITS_DEFAULT) {
            binding.kelvinBtn.isChecked = true
        }
        if (settings?.unit == Constant.UNITS_FAHRENHEIT) {
            binding.fehrenBtn.isChecked = true
        }
        if (settings?.unit == Constant.UNITS_CELSIUS) {
            binding.celsBtn.isChecked = true
        }
        if (settings?.lang == Constant.LANG_EN) {
            binding.radioEnglish.isChecked = true
        }
        if (settings?.lang == Constant.LANG_AR) {
            binding.radioArabic.isChecked = true
        }
    }


}