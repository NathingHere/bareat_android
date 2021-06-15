package com.example.bareat_android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.DataBook
import com.example.data.Image
import com.example.data.fold
import com.example.domain.usecase.profile.GetBookListUseCase
import com.example.domain.usecase.restaurant.DeleteBookUseCase
import com.example.domain.usecase.restaurant.ParamsDeleteBook
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getBookListUseCase: GetBookListUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : BaseViewModel() {

    sealed class BookState {
        object Empty : BookState()
        data class SUCCESS(val bookList: List<DataBook>) : BookState()
        data class ERROR(val errorMessage: String) : BookState()
    }

    sealed class DeleteState {
        data class SUCCESS(val any: Any) : DeleteState()
        data class ERROR(val errorMessage: String) : DeleteState()
    }

    private val _bookMutableData = MutableLiveData<ScreenState<BookState>>()
    val bookListData: LiveData<ScreenState<BookState>>
        get() = _bookMutableData

    private val _deleteMutableData = MutableLiveData<ScreenState<DeleteState>>()
    val deleteListData: LiveData<ScreenState<DeleteState>>
        get() = _deleteMutableData

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

    private fun updateBookUI(state:ScreenState<BookState>) {
        _bookMutableData.postValue(state)
    }

    private fun updateDeleteUI(state:ScreenState<DeleteState>) {
        _deleteMutableData.postValue(state)
    }

}