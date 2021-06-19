package com.example.bareat_android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.*
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import com.example.domain.usecase.profile.*
import com.example.domain.usecase.restaurant.DeleteBookUseCase
import com.example.domain.usecase.restaurant.ParamsDeleteBook
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getBookListUseCase: GetBookListUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val getUserRestaurantReviewsUseCase: GetUserRestaurantReviewsUseCase,
    private val getUserProductReviewsUseCase: GetUserProductReviewsUseCase,
    private val editRestaurantReviewUseCase: EditRestaurantReviewUseCase,
    private val editProductReviewUseCase: EditProductReviewUseCase,
    private val deleteProductReviewUseCase: DeleteProductReviewUseCase,
    private val deleteRestaurantReviewUseCase: DeleteRestaurantReviewUseCase
) : BaseViewModel() {

    sealed class BookState {
        object Empty : BookState()
        data class SUCCESS(val bookList: List<DataBook>) : BookState()
        data class ERROR(val errorMessage: String) : BookState()
    }

    sealed class ProductReviewState {
        object Empty : ProductReviewState()
        data class SUCCESS(val bookList: List<ReviewDish>) : ProductReviewState()
        data class ERROR(val errorMessage: String) : ProductReviewState()
    }

    sealed class RestaurantReviewState {
        object Empty : RestaurantReviewState()
        data class SUCCESS(val bookList: List<ReviewRestaurant>) : RestaurantReviewState()
        data class ERROR(val errorMessage: String) : RestaurantReviewState()
    }

    sealed class DeleteState {
        data class SUCCESS(val any: Any) : DeleteState()
        data class ERROR(val errorMessage: String) : DeleteState()
    }

    sealed class EditState {
        data class SUCCESS(val any: Any) : EditState()
        data class ERROR(val errorMessage: String) : EditState()
    }

    private val _bookMutableData = MutableLiveData<ScreenState<BookState>>()
    val bookListData: LiveData<ScreenState<BookState>>
        get() = _bookMutableData

    private val _productReviewMutableData = MutableLiveData<ScreenState<ProductReviewState>>()
    val productReviewData: LiveData<ScreenState<ProductReviewState>>
        get() = _productReviewMutableData

    private val _restaurantReviewMutableData = MutableLiveData<ScreenState<RestaurantReviewState>>()
    val restaurantReviewData: LiveData<ScreenState<RestaurantReviewState>>
        get() = _restaurantReviewMutableData

    private val _deleteMutableData = MutableLiveData<ScreenState<DeleteState>>()
    val deleteListData: LiveData<ScreenState<DeleteState>>
        get() = _deleteMutableData

    private val _editMutableData = MutableLiveData<ScreenState<EditState>>()
    val editListData: LiveData<ScreenState<EditState>>
        get() = _editMutableData

    fun getBookList(id: Int) {
        updateBookUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getBookListUseCase.execute(id) } }

            result.await().fold(
                { updateBookUI(ScreenState.RenderData(BookState.ERROR(it))) },
                {
                    if (it.isEmpty()) {
                        updateBookUI(ScreenState.RenderData(BookState.Empty))
                    } else {
                        updateBookUI(ScreenState.RenderData(BookState.SUCCESS(it)))
                    }
                }
            )
        }
    }

    fun getReviewProductList(id: Int) {
        updateProductReviewUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getUserProductReviewsUseCase.execute(id) } }

            result.await().fold(
                { updateProductReviewUI(ScreenState.RenderData(ProductReviewState.ERROR(it))) },
                {
                    if (it.isEmpty()) {
                        updateProductReviewUI(ScreenState.RenderData(ProductReviewState.Empty))
                    } else {
                        updateProductReviewUI(ScreenState.RenderData(ProductReviewState.SUCCESS(it)))
                    }
                }
            )
        }
    }

    fun getReviewRestaurantList(id: Int) {
        updateRestaurantReviewUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { getUserRestaurantReviewsUseCase.execute(id) } }

            result.await().fold(
                { updateRestaurantReviewUI(ScreenState.RenderData(RestaurantReviewState.ERROR(it))) },
                {
                    if (it.isEmpty()) {
                        updateRestaurantReviewUI(ScreenState.RenderData(RestaurantReviewState.Empty))
                    } else {
                        updateRestaurantReviewUI(ScreenState.RenderData(RestaurantReviewState.SUCCESS(it)))
                    }
                }
            )
        }
    }

    fun deleteBook(userId: Int, bookId: Int) {
        updateDeleteUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { deleteBookUseCase.execute(
                ParamsDeleteBook(userId, bookId)
            ) } }

            result.await().fold(
                { updateDeleteUI(ScreenState.RenderData(DeleteState.ERROR(it))) },
                { updateDeleteUI(ScreenState.RenderData(DeleteState.SUCCESS(it))) }
            )
        }
    }

    fun deleteProductReview(bookId: Int) {
        updateDeleteUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { deleteProductReviewUseCase.execute(bookId) } }

            result.await().fold(
                { updateDeleteUI(ScreenState.RenderData(DeleteState.ERROR(it))) },
                { updateDeleteUI(ScreenState.RenderData(DeleteState.SUCCESS(it))) }
            )
        }
    }

    fun deleteRestaurantReview(bookId: Int) {
        updateDeleteUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { deleteRestaurantReviewUseCase.execute(bookId) } }

            result.await().fold(
                { updateDeleteUI(ScreenState.RenderData(DeleteState.ERROR(it))) },
                { updateDeleteUI(ScreenState.RenderData(DeleteState.SUCCESS(it))) }
            )
        }
    }

    fun editRestaurantReview(reviewId: Int, rateRestaurantBody: RateRestaurantBody) {
        updateEditUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { editRestaurantReviewUseCase.execute(ParamsEditRestaurant(reviewId, rateRestaurantBody)) } }

            result.await().fold(
                { updateEditUI(ScreenState.RenderData(EditState.ERROR(it))) },
                { updateEditUI(ScreenState.RenderData(EditState.SUCCESS(it))) }
            )
        }
    }

    fun editDishReview(reviewId: Int, rateDishBody: RateDishBody) {
        updateEditUI(ScreenState.LOADING)
        uiScope.launch {
            val result = uiScope.async { withContext(ioContext) { editProductReviewUseCase.execute(ParamsEditDish(reviewId, rateDishBody)) } }

            result.await().fold(
                { updateEditUI(ScreenState.RenderData(EditState.ERROR(it))) },
                { updateEditUI(ScreenState.RenderData(EditState.SUCCESS(it))) }
            )
        }
    }

    private fun updateBookUI(state:ScreenState<BookState>) {
        _bookMutableData.postValue(state)
    }

    private fun updateProductReviewUI(state:ScreenState<ProductReviewState>) {
        _productReviewMutableData.postValue(state)
    }

    private fun updateRestaurantReviewUI(state:ScreenState<RestaurantReviewState>) {
        _restaurantReviewMutableData.postValue(state)
    }

    private fun updateDeleteUI(state:ScreenState<DeleteState>) {
        _deleteMutableData.postValue(state)
    }

    private fun updateEditUI(state:ScreenState<EditState>) {
        _editMutableData.postValue(state)
    }
}