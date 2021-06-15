package com.example.bareat_android.ui.dish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.Dish
import com.example.data.ReviewDish
import com.example.data.fold
import com.example.data.torecieve.RateDishResponse
import com.example.data.tosend.RateDishBody
import com.example.domain.usecase.dish.GetCommentListDishUseCase
import com.example.domain.usecase.dish.ParamsRateDish
import com.example.domain.usecase.dish.RateDishUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DishViewModel(
    private val rateDishUseCase: RateDishUseCase,
    private val getCommentListDishUseCase: GetCommentListDishUseCase
) : BaseViewModel() {

    sealed class RateDishState {

        data class SUCCESS(val rateDishResponse: RateDishResponse) : RateDishState()
        data class ERROR(val errorMessage: String) : RateDishState()
    }

    sealed class CommentListDishState {
        object Empty : CommentListDishState()
        data class SUCCESS(val commentList: List<ReviewDish>) : CommentListDishState()
        data class ERROR(val errorMessage: String) : CommentListDishState()
    }

    private val _rateDishMutableData = MutableLiveData<ScreenState<RateDishState>>()
    val rateDishliveData: LiveData<ScreenState<RateDishState>>
        get() = _rateDishMutableData

    private val _commentListMutableData = MutableLiveData<ScreenState<CommentListDishState>>()
    val commentListliveData: LiveData<ScreenState<CommentListDishState>>
        get() = _commentListMutableData

    fun rateDish(userId: Int, dishId: Int, rateDishBody: RateDishBody) {
        updateUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { rateDishUseCase.execute(
                ParamsRateDish(userId, dishId, rateDishBody)
            ) } }
            result.await().fold(
                { updateUI(ScreenState.RenderData(RateDishState.ERROR(it))) },
                { updateUI(ScreenState.RenderData(RateDishState.SUCCESS(it))) }
            )
        }
    }

    fun getCommentList(dishId: Int) {
        updateCommentListUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getCommentListDishUseCase.execute(dishId) } }
            result.await().fold(
                { updateCommentListUI(ScreenState.RenderData(CommentListDishState.ERROR(it))) },
                { if (it.isEmpty()) {
                    updateCommentListUI(ScreenState.RenderData(CommentListDishState.Empty))
                } else {
                    updateCommentListUI(ScreenState.RenderData(CommentListDishState.SUCCESS(it)))
                }
                }
            )
        }
    }

    private fun updateUI(state: ScreenState<RateDishState>) {
        _rateDishMutableData.postValue(state)
    }

    private fun updateCommentListUI(state: ScreenState<CommentListDishState>) {
        _commentListMutableData.postValue(state)
    }
}