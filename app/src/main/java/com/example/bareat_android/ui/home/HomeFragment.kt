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
                    onRatedClick = {ratedList()},
                    onPopularityClick = {popularityList()},
                    onAlphabeticalClick = {alphabeticalList()})
        }
    }

    override fun initView() {
        with(binding) {

            tvFilter.setOnClickListener {
                navController?.navigate(routeToRestaurant())
            }

        }
    }

    private fun alphabeticalList() {
        binding.tvFilter.text = getString(R.string.alphabetically)
    }

    private fun popularityList() {
        binding.tvFilter.text = getString(R.string.popularity)
    }

    private fun ratedList() {
        binding.tvFilter.text = getString(R.string.rated)
    }

}