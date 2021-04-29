package com.example.bareat_android.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentSearchBinding
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.adapter.RestaurantAdapter
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.home.HomeFragmentDirections
import com.example.bareat_android.ui.home.HomeViewModel
import com.example.bareat_android.ui.search.SearchFragmentDirections.Companion.routeToRestaurant
import com.example.data.Restaurant
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var restaurantAdapter: RestaurantAdapter

    private var restaurantList = listOf<Restaurant>()

    override fun initializeBinding(): FragmentSearchBinding {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.EmptyItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_search))
        }
    }

    override fun initView() {

        searchViewModel.init()

        with(binding) {

            restaurantAdapter = RestaurantAdapter(layoutInflater = layoutInflater) {
                onRestaurantClick(it)
            }

            rvRestaurants.initVerticalRecycler(restaurantAdapter)

        }

        searchViewModel.restaurantListData.observe(viewLifecycleOwner) { manageRestaurantScreenState(it) }

    }

    private fun manageRestaurantScreenState(state: BaseViewModel.ScreenState<SearchViewModel.RestaurantState>?) {
        when (state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                menageRestaurantState(state.renderState)
            }
        }
    }

    private fun menageRestaurantState(state: SearchViewModel.RestaurantState) {
        hideProgressDialog()
        when (state) {
            is SearchViewModel.RestaurantState.SUCCESS -> {
                restaurantList = state.restaurantList
                restaurantAdapter.updateList(state.restaurantList)
            }
            is SearchViewModel.RestaurantState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun onRestaurantClick(restaurant: Restaurant) {
        navController?.navigate(routeToRestaurant(restaurant))
    }

}