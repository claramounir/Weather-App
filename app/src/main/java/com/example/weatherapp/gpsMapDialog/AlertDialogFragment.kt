package com.example.weatherapp.gpsMapDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AlertDialogFragment : Fragment() {


    val itemList = arrayOf("Gps", "Map")

    private var selectedLocationIndex: Int = 0
    private val location= arrayOf("GPS","Map")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDailog()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    companion object {


    }

    fun showDailog() {

        var selectedFruits = location[selectedLocationIndex]
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Please, Choose your location ")
            .setSingleChoiceItems(location, selectedLocationIndex) { dialog_, which ->
                selectedLocationIndex = which
                selectedFruits = location[which]
            }
            .setPositiveButton("Ok") { dialog, which ->
                if (selectedFruits.equals("GPS")){
                    val action=
                        AlertDialogFragmentDirections.actionAlertDialogFragmentToHomeFragment()
                    findNavController().navigate(action)

                }else{
                    val action=
                        AlertDialogFragmentDirections.actionAlertDialogFragmentToMapsFragment(false,false,0)
                    findNavController().navigate(action)
                }
                Toast.makeText(requireActivity(), "$selectedFruits Selected", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}