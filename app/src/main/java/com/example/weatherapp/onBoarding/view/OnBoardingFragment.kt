package com.example.weatherapp.onBoarding.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.weatherapp.R


class OnBoardingFragment : Fragment() {

    private lateinit  var viewPager: ViewPager
    private lateinit var dotLayout: LinearLayout
    private lateinit var dots: Array<TextView?>
    private lateinit  var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar
        viewPager = view.findViewById(R.id.slideViewPager)
        dotLayout = view.findViewById(R.id.indicator_layout)
        viewPagerAdapter = ViewPagerAdapter(requireContext())
        viewPager?.setAdapter(viewPagerAdapter)
        setUpIndicator(0)
        viewPager?.addOnPageChangeListener(viewListener)
    }

    fun setUpIndicator(position: Int) {
        dots = arrayOfNulls(4)
        dotLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(requireContext())
            dots[i]!!.text = Html.fromHtml("&#8226")
            dots[i]!!.textSize = 35f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i]!!.setTextColor(
                    resources.getColor(
                        R.color.dark_purple,
                        requireContext().theme
                    )
                )
            }
            dotLayout!!.addView(dots[i])
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position]!!.setTextColor(
                resources.getColor(
                    R.color.light_purple,
                    requireContext().theme
                )
            )
        }
    }

    var viewListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }

}


//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
//    }
