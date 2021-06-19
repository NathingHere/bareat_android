package com.example.bareat_android.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.bareat_android.R
import com.example.bareat_android.databinding.BookItemviewBinding
import com.example.bareat_android.databinding.FragmentProfileBinding
import com.example.bareat_android.databinding.ViewpagerImageBinding
import com.example.bareat_android.setup.extensions.gone
import com.example.bareat_android.setup.extensions.initHorizontalRecycler
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.adapter.*
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.*
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.dialog_rating.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private var commentRating = 5

    private val profileViewModel by viewModel<ProfileViewModel>()

    private val bookListAdapter = BookListAdapter() {
        when(it) {
            is BookActionItem.Route -> { navController?.navigate(ProfileFragmentDirections.routeToRestaurant(it.restaurant)) }
            is BookActionItem.Delete -> { onBookClick(it.book)  }
        }
    }

    private lateinit var dishAdapter: ReviewUserDishAdapter

    private lateinit var restaurantAdapter: ReviewUserAdapter

    private lateinit var dialog: Dialog

    private var bookList = listOf<DataBook>()
    private var reviewRestaurantList = listOf<ReviewRestaurant>()
    private var reviewDishList = listOf<ReviewDish>()

    override fun initializeBinding(): FragmentProfileBinding {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.ProfileItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_profile))
            setOnMenuItemClick(onSettingsClick = {showLogoutDialog()})
        }
    }

    override fun initView() {
        profileViewModel.init()

        initAdapter()

        with(binding) {

            rvBook.initVerticalRecycler(bookListAdapter)
            rvDishes.initVerticalRecycler(dishAdapter)
            rvRestaurant.initVerticalRecycler(restaurantAdapter)

            prefs.id?.let { profileViewModel.getBookList(it.toInt()) }
            profileViewModel.bookListData.observe(viewLifecycleOwner) { manageBookScreenState(it) }
            prefs.id?.let { profileViewModel.getReviewProductList(it.toInt()) }
            profileViewModel.productReviewData.observe(viewLifecycleOwner) { manageProductReviewScreenState(it) }
            prefs.id?.let { profileViewModel.getReviewRestaurantList(it.toInt()) }
            profileViewModel.restaurantReviewData.observe(viewLifecycleOwner) { manageRestaurantReviewScreenState(it) }
            profileViewModel.deleteListData.observe(viewLifecycleOwner) { manageDeleteScreenState(it) }
            profileViewModel.editListData.observe(viewLifecycleOwner) { manageEditScreenState(it) }
        }
    }

    private fun manageEditScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.EditState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageEditState(state.renderState)
            }
        }
    }

    private fun manageEditState(state: ProfileViewModel.EditState) {
        hideProgressDialog()
        when(state){
            is ProfileViewModel.EditState.SUCCESS -> {
                showToast("Se ha editado con éxito")
            }
            is ProfileViewModel.EditState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageRestaurantReviewScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.RestaurantReviewState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageRestaurantReviewState(state.renderState)
            }
        }
    }

    private fun manageRestaurantReviewState(state: ProfileViewModel.RestaurantReviewState) {
        hideProgressDialog()
        when(state){
            ProfileViewModel.RestaurantReviewState.Empty -> {
                binding.tvEmptyRestaurant.visible()
            }
            is ProfileViewModel.RestaurantReviewState.SUCCESS -> {
                reviewRestaurantList = state.bookList
                restaurantAdapter.updateList(state.bookList)
            }
            is ProfileViewModel.RestaurantReviewState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageProductReviewScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.ProductReviewState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageProductReviewState(state.renderState)
            }
        }
    }

    private fun manageProductReviewState(state: ProfileViewModel.ProductReviewState) {
        hideProgressDialog()
        when(state){
            ProfileViewModel.ProductReviewState.Empty -> {
                binding.tvEmptyDish.visible()
            }
            is ProfileViewModel.ProductReviewState.SUCCESS -> {
                reviewDishList = state.bookList
                dishAdapter.updateList(state.bookList)
            }
            is ProfileViewModel.ProductReviewState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageDeleteScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.DeleteState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageDeleteState(state.renderState)
            }
        }
    }

    private fun manageDeleteState(state: ProfileViewModel.DeleteState) {
        hideProgressDialog()
        when(state){
            is ProfileViewModel.DeleteState.SUCCESS -> {
                showToast("Se ha eliminado con éxito")
            }
            is ProfileViewModel.DeleteState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageBookScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.BookState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageBookState(state.renderState)
            }
        }
    }

    private fun manageBookState(state: ProfileViewModel.BookState) {
        hideProgressDialog()
        when(state){
            ProfileViewModel.BookState.Empty -> {
                binding.tvEmptyBook.visible()
            }
            is ProfileViewModel.BookState.SUCCESS -> {
                bookList = state.bookList
                bookListAdapter.updateList(state.bookList)
            }
            is ProfileViewModel.BookState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun initAdapter() {
        dishAdapter = ReviewUserDishAdapter(layoutInflater) {
            when(it) {
                is ReviewDishActionItem.Route -> { navController?.navigate(ProfileFragmentDirections.routeToDish(it.dish)) }
                is ReviewDishActionItem.Edit -> { editDishReview(it.review) }
                is ReviewDishActionItem.Delete -> { deleteDishReview(it.review) }
            }
        }

        restaurantAdapter = ReviewUserAdapter(layoutInflater) {
            when(it) {
                is ReviewRestaurantActionItem.Route -> { navController?.navigate(ProfileFragmentDirections.routeToRestaurant(it.restaurant)) }
                is ReviewRestaurantActionItem.Edit -> { editReview(it.review) }
                is ReviewRestaurantActionItem.Delete -> { deleteReview(it.review) }
            }
        }
    }

    inner class BookListAdapter(private val onClick: (BookActionItem) -> Unit) : BaseRecyclerView<DataBook, BookListAdapter.Holder>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
            BookItemviewBinding.inflate(layoutInflater, parent, false))

        inner class Holder(private val bookItemviewBinding: BookItemviewBinding) : BaseViewHolder<DataBook>(bookItemviewBinding) {

            override fun bindData(item: DataBook) {
                with(bookItemviewBinding) {
                    tvName.text = item.restaurant?.name ?: ""
                    tvDate.text = item.date
                    tvTime.text = item.hour?.length?.minus(3)?.let { item.hour!!.substring(0, it) }
                    tvNumberPerson.text = item.num.toString()
                    btnDelete.setOnClickListener { onClick.invoke(BookActionItem.Delete(item)) }
                    root.setOnClickListener {
                        item.restaurant?.let { it1 ->
                            BookActionItem.Route(
                                it1
                            )
                        }?.let { it2 -> onClick.invoke(it2) }
                    }
                }
            }

        }

    }

    private fun showLogoutDialog() {
        context?.let { it ->
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_logout)

                with(binding) {
                    closeYes.setOnClickListener {
                        prefs.clearLogin()
                        homeActivity().routeToLogin()
                    }
                    closeNo.setOnClickListener { dismiss() }
                }

                //Change window options:
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
                window?.attributes = lp
            }
        }
    }

    private fun onBookClick(dataBook: DataBook) {
        dataBook.userId?.let { dataBook.id?.let { it1 -> profileViewModel.deleteBook(it, it1) } }
        bookList = bookList.filter { it.id != dataBook.id }
        bookListAdapter.updateList(bookList)
    }

    private fun deleteDishReview(dish: ReviewDish) {
        profileViewModel.deleteProductReview(dish.id)
        reviewDishList = reviewDishList.filter { it.id != dish.id }
        dishAdapter.updateList(reviewDishList)
    }

    private fun deleteReview(restaurant: ReviewRestaurant) {
        profileViewModel.deleteRestaurantReview(restaurant.id)
        reviewRestaurantList = reviewRestaurantList.filter { it.id != restaurant.id }
        restaurantAdapter.updateList(reviewRestaurantList)
    }

    private fun editDishReview(review: ReviewDish) {

        context?.let { it ->
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_rating)

                with(binding) {
                    ratingBarReview.setOnRatingBarChangeListener { _, rating, _ ->
                        commentRating = (rating * 2).toInt()
                    }
                    commentYes.setOnClickListener {
                        if(inputComment.text.isNotEmpty()) {
                            profileViewModel.editDishReview(review.id, RateDishBody(commentRating, inputComment.text.toString()))
                            reviewDishList.find{ it.id == review.id }?.rating = commentRating.toFloat()
                            reviewDishList.find{ it.id == review.id }?.comment = inputComment.text.toString()
                            dishAdapter.updateList(reviewDishList)
                            dismiss()
                        } else showToast("Debe escribir un comentario")
                    }
                    commentNo.setOnClickListener { dismiss() }
                }

                //Change window options:
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
                window?.attributes = lp
            }
        }
    }

    private fun editReview(review: ReviewRestaurant) {

        context?.let { it ->
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_rating)

                with(binding) {
                    ratingBarReview.setOnRatingBarChangeListener { _, rating, _ ->
                        commentRating = (rating * 2).toInt()
                    }
                    commentYes.setOnClickListener {
                        if(inputComment.text.isNotEmpty()) {
                            profileViewModel.editRestaurantReview(review.id, RateRestaurantBody(commentRating, inputComment.text.toString()))
                            reviewRestaurantList.find{ it.id == review.id }?.rating = commentRating.toFloat()
                            reviewRestaurantList.find{ it.id == review.id }?.comment = inputComment.text.toString()
                            restaurantAdapter.updateList(reviewRestaurantList)
                            dismiss()
                        } else showToast("Debe escribir un comentario")
                    }
                    commentNo.setOnClickListener { dismiss() }
                }

                //Change window options:
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
                window?.attributes = lp
            }
        }
    }

}