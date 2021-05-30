package com.example.bareat_android.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentHomeBinding
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.adapter.RestaurantAdapter
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.data.Restaurant
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var restaurantAdapter: RestaurantAdapter

    private var restaurantList = listOf<Restaurant>()

    override fun initializeBinding(): FragmentHomeBinding {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.HomeItemHot)
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

        homeViewModel.init()

        with(binding) {

            restaurantAdapter = RestaurantAdapter(layoutInflater = layoutInflater) {
                onRestaurantClick(it)
            }

            rvRestaurants.initVerticalRecycler(restaurantAdapter)
        }

        homeViewModel.restaurantListData.observe(viewLifecycleOwner) { manageRestaurantScreenState(it) }
    }

    private fun manageRestaurantScreenState(state: BaseViewModel.ScreenState<HomeViewModel.RestaurantState>?) {
        when (state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                menageRestaurantState(state.renderState)
            }
        }
    }

    private fun menageRestaurantState(state: HomeViewModel.RestaurantState) {
        hideProgressDialog()
        when (state) {
            is HomeViewModel.RestaurantState.SUCCESS -> {
                restaurantList = state.restaurantList
                restaurantAdapter.updateList(state.restaurantList)
            }
            is HomeViewModel.RestaurantState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun onRestaurantClick(restaurant: Restaurant) {
        navController?.navigate(HomeFragmentDirections.routeToRestaurant(restaurant))
    }

    private fun alphabeticalList() {
        binding.tvFilter.text = getString(R.string.alphabetically)
        val sortedList = restaurantList.sortedBy { it.name }
        restaurantAdapter.updateList(sortedList)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.HomeItemAlphabetically)
    }

    private fun popularityList() {
        binding.tvFilter.text = getString(R.string.popularity)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.HomeItemHot)
    }

    private fun ratedList() {
        binding.tvFilter.text = getString(R.string.rated)
        val sortedList = restaurantList.sortedByDescending { it.rating }
        restaurantAdapter.updateList(sortedList)

        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.HomeItemRated)
    }

}