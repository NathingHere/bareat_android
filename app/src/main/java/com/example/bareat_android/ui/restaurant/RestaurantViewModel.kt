package com.example.bareat_android.ui.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.data.Dish
import com.example.data.ReviewRestaurant
import com.example.data.fold
import com.example.domain.usecase.dish.GetDishListUseCase
import com.example.domain.usecase.review.GetReviewListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel(
        private val getDishListUseCase: GetDishListUseCase,
        private val getReviewListUseCase: GetReviewListUseCase
) : BaseViewModel() {

    sealed class DishState {
        data class SUCCESS(val dishList: List<Dish>) : DishState()
        data class ERROR(val errorMessage: String) : DishState()
    }

    sealed class ReviewState {
        object Empty : ReviewState()
        data class SUCCESS(val reviewRestaurantList: List<ReviewRestaurant>) : ReviewState()
        data class ERROR(val errorMessage: String) : ReviewState()
    }

    private val _dishMutableData = MutableLiveData<ScreenState<DishState>>()
    val dishListData: LiveData<ScreenState<DishState>>
        get() = _dishMutableData

    private val _reviewMutableData = MutableLiveData<ScreenState<ReviewState>>()
    val reviewListData: LiveData<ScreenState<ReviewState>>
        get() = _reviewMutableData

    fun getDishList(id: Int) {
        updateUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getDishListUseCase.execute(id) } }

            result.await().fold(
                    { updateUI(ScreenState.RenderData(DishState.ERROR(it))) },
                    { updateUI(ScreenState.RenderData(DishState.SUCCESS(it))) }
            )
        }
    }

    fun getReviewList(id: Int) {
        updateReviewUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getReviewListUseCase.execute(id) } }

            result.await().fold(
                    { updateReviewUI(ScreenState.RenderData(ReviewState.ERROR(it))) },
                    {
                        if (it.isEmpty()) {
                            updateReviewUI(ScreenState.RenderData(ReviewState.Empty))
                        } else {
                            updateReviewUI(ScreenState.RenderData(ReviewState.SUCCESS(it)))
                        }
                    }
            )
        }
    }

    private fun updateUI(state: ScreenState<DishState>) {
        _dishMutableData.postValue(state)
    }

    private fun updateReviewUI(state:ScreenState<ReviewState>) {
        _reviewMutableData.postValue(state)
    }

}