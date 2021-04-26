package com.example.bareat_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentHomeBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.home.HomeFragmentDirections.Companion.routeToRestaurant

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initializeBinding(): FragmentHomeBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.HomeItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_home))
            setOnMenuItemClick (
                    onRatedClick = {},
                    onPopularityClick = {},
                    onAlphabeticalClick = {})
        }
    }
    
    override fun initView() {
        with(binding) {

            tvFilter.setOnClickListener {
                navController?.navigate(routeToRestaurant())
            }

        }
    }

}