package com.example.bareat_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.data.Restaurant
import com.example.data.fold
import com.example.domain.usecase.restaurant.GetRestaurantRatedListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getRestaurantRatedListUseCase: GetRestaurantRatedListUseCase
) : BaseViewModel() {

    sealed class RestaurantState {
        data class SUCCESS(val restaurantList: List<Restaurant>) : RestaurantState()
        data class ERROR(val errorMessage: String) : RestaurantState()
    }

    private val _restaurantMutableData = MutableLiveData<ScreenState<RestaurantState>>()
    val restaurantListData: LiveData<ScreenState<RestaurantState>>
        get() = _restaurantMutableData

    override fun onCreate() {
        super.onCreate()
        getRestaurantRatedList()
    }

    private fun getRestaurantRatedList() {
        updateUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getRestaurantRatedListUseCase.execute() } }

            result.await().fold(
                { updateUI(ScreenState.RenderData(RestaurantState.ERROR(it))) },
                { updateUI(ScreenState.RenderData(RestaurantState.SUCCESS(it))) }
            )
        }
    }

    private fun updateUI(state: ScreenState<RestaurantState>) {
        _restaurantMutableData.postValue(state)
    }

}