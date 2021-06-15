package com.example.bareat_android.ui.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.dish.DishViewModel
import com.example.data.Dish
import com.example.data.Image
import com.example.data.ReviewRestaurant
import com.example.data.fold
import com.example.data.torecieve.BookResponse
import com.example.data.torecieve.RateDishResponse
import com.example.data.torecieve.RateRestaurantResponse
import com.example.data.tosend.BookBody
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.usecase.dish.GetDishListUseCase
import com.example.domain.usecase.dish.ParamsRateDish
import com.example.domain.usecase.restaurant.*
import com.example.domain.usecase.review.GetReviewListUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel(
        private val getDishListUseCase: GetDishListUseCase,
        private val getReviewListUseCase: GetReviewListUseCase,
        private val getImageListUseCase: GetImageListUseCase,
        private val bookRestaurantUseCase: BookRestaurantUseCase,
        private val rateRestaurantUseCase: RateRestaurantUseCase
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

    sealed class ImageState {
        data class SUCCESS(val imageList: List<Image>) : ImageState()
        data class ERROR(val errorMessage: String) : ImageState()
    }

    sealed class RateRestaurantState {
        data class SUCCESS(val rateRestaurantResponse: RateRestaurantResponse) : RateRestaurantState()
        data class ERROR(val errorMessage: String) : RateRestaurantState()
    }

    sealed class BookRestaurantState {
        data class SUCCESS(val bookResponse: BookResponse) : BookRestaurantState()
        data class ERROR(val errorMessage: String) : BookRestaurantState()
    }

    private val _dishMutableData = MutableLiveData<ScreenState<DishState>>()
    val dishListData: LiveData<ScreenState<DishState>>
        get() = _dishMutableData

    private val _reviewMutableData = MutableLiveData<ScreenState<ReviewState>>()
    val reviewListData: LiveData<ScreenState<ReviewState>>
        get() = _reviewMutableData

    private val _imageMutableData = MutableLiveData<ScreenState<ImageState>>()
    val imageListData: LiveData<ScreenState<ImageState>>
        get() = _imageMutableData

    private val _rateRestaurantMutableData = MutableLiveData<ScreenState<RateRestaurantState>>()
    val rateRestaurantListData: LiveData<ScreenState<RateRestaurantState>>
        get() = _rateRestaurantMutableData

    private val _bookMutableData = MutableLiveData<ScreenState<BookRestaurantState>>()
    val bookListData: LiveData<ScreenState<BookRestaurantState>>
        get() = _bookMutableData

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

    fun getImageList(id: Int) {
        updateImageListUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getImageListUseCase.execute(id) } }

            result.await().fold(
                { updateImageListUI(ScreenState.RenderData(ImageState.ERROR(it))) },
                { updateImageListUI(ScreenState.RenderData(ImageState.SUCCESS(it))) }
            )
        }
    }

    fun rateRestaurant(userId: Int, restaurantId: Int, rateRestaurantBody: RateRestaurantBody) {
        updateRateRestaurantUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { rateRestaurantUseCase.execute(
                ParamsRateRestaurant(userId, restaurantId, rateRestaurantBody)
            ) } }
            result.await().fold(
                { updateRateRestaurantUI(ScreenState.RenderData(RateRestaurantState.ERROR(it))) },
                { updateRateRestaurantUI(ScreenState.RenderData(RateRestaurantState.SUCCESS(it))) }
            )
        }
    }

    fun bookRestaurant(userId: Int, restaurantId: Int, bookBody: BookBody) {
        updateBookUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { bookRestaurantUseCase.execute(
                ParamsBookRestaurant(userId, restaurantId, bookBody)
            ) } }
            result.await().fold(
                { updateBookUI(ScreenState.RenderData(BookRestaurantState.ERROR(it))) },
                { updateBookUI(ScreenState.RenderData(BookRestaurantState.SUCCESS(it))) }
            )
        }
    }

    private fun updateUI(state: ScreenState<DishState>) {
        _dishMutableData.postValue(state)
    }

    private fun updateReviewUI(state:ScreenState<ReviewState>) {
        _reviewMutableData.postValue(state)
    }

    private fun updateImageListUI(state: ScreenState<ImageState>) {
        _imageMutableData.postValue(state)
    }

    private fun updateRateRestaurantUI(state: ScreenState<RateRestaurantState>) {
        _rateRestaurantMutableData.postValue(state)
    }

    private fun updateBookUI(state: ScreenState<BookRestaurantState>) {
        _bookMutableData.postValue(state)
    }

}