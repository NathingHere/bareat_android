package com.example.bareat_android.ui.search

import android.app.ActionBar
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.SearchView
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentSearchBinding
import com.example.bareat_android.setup.extensions.gone
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.adapter.RestaurantAdapter
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.search.SearchFragmentDirections.Companion.routeToRestaurant
import com.example.data.Restaurant
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var restaurantAdapter: RestaurantAdapter

    private var fullRestaurantList = listOf<Restaurant>()
    private var restaurantList = listOf<Restaurant>()

    override fun initializeBinding(): FragmentSearchBinding {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.SearchItemEmpty)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_search))
            setOnMenuItemClick (
                onNothingClick = {nothingFilter()},
                onBarClick = {barFilter()},
                onRestaurantClick = {restaurantFilter()},
                onCafeClick = {cafeFilter()}
            )
        }
    }

    override fun initView() {

        searchViewModel.init()

        with(binding) {

            restaurantAdapter = RestaurantAdapter(layoutInflater = layoutInflater) {
                onRestaurantClick(it)
            }

            rvRestaurants.initVerticalRecycler(restaurantAdapter)

            search.addTextChangedListener(object : TextWatcher{

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filterList(p0.toString())
                }

            })

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
                fullRestaurantList = state.restaurantList
                restaurantAdapter.updateList(state.restaurantList)
            }
            is SearchViewModel.RestaurantState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun filterList(filterItem: String) {
        val tempList = mutableListOf<Restaurant>()

        for(row in restaurantList) {

            if(filterItem.toLowerCase() in row.name.toString().toLowerCase()) {

                tempList += row

            }

            restaurantAdapter.updateList(tempList)

        }

        if(tempList.count() == 0)
            binding.tvEmpty.visible()
        else
            binding.tvEmpty.gone()

    }

    private fun onRestaurantClick(restaurant: Restaurant) {
        navController?.navigate(routeToRestaurant(restaurant))
    }

    private fun cafeFilter() {
        restaurantList = fullRestaurantList
        binding.search.setText("")
        restaurantList = restaurantList.filter { it.type == "Cafetería" }
        restaurantAdapter.updateList(restaurantList)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.SearchItemCafe)
    }

    private fun restaurantFilter() {
        restaurantList = fullRestaurantList
        binding.search.setText("")
        restaurantList = restaurantList.filter { it.type == "Restaurante" }
        restaurantAdapter.updateList(restaurantList)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.SearchItemRestaurant)
    }

    private fun barFilter() {
        restaurantList = fullRestaurantList
        binding.search.setText("")
        restaurantList = restaurantList.filter { it.type == "Bar" }
        restaurantAdapter.updateList(restaurantList)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.SearchItemBar)
    }

    private fun nothingFilter() {
        restaurantList = fullRestaurantList
        binding.search.setText("")
        restaurantAdapter.updateList(restaurantList)
        provideToolbar().initToolbar(BareatToolbar.ToolbarItemMenu.SearchItemEmpty)
    }

}