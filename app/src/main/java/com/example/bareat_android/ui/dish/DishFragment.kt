package com.example.bareat_android.ui.dish

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentDishBinding
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.adapter.ReviewAdapter
import com.example.bareat_android.ui.adapter.ReviewDishAdapter
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.restaurant.RestaurantFragmentArgs
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant
import com.example.data.tosend.RateDishBody
import com.example.data.tosend.RateRestaurantBody
import kotlinx.android.synthetic.main.dialog_rating.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DishFragment : BaseFragment<FragmentDishBinding>() {

    private val args by navArgs<DishFragmentArgs>()

    private val dishViewModel by viewModel<DishViewModel>()

    private var commentRating = 5

    private lateinit var dialog: Dialog

    private lateinit var reviewListAdapter : ReviewDishAdapter
    private lateinit var reviewList:List<ReviewDish>

    override fun initializeBinding(): FragmentDishBinding {
        binding = FragmentDishBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.EmptyItem)
            visible()
            hideDoneCancelButtons()
            setSmallToolbarTitle(args.currentDish.name ?: "")
            setNavIcon(R.drawable.ic_left_arrow) {
                homeActivity().onBackPressed()
            }
        }
    }

    override fun initView() {
        dishViewModel.init()

        with(binding) {
            tvName.text = args.currentDish.name
            ivDish.load(args.currentDish.cover)
            val rating = args.currentDish.rating?.div(2f)
            rating?.let {
                ratingBar.rating = it
            }
            tvDescription.text = args.currentDish.desc
            tvScore.text = getString(R.string.score, rating)
            tvType.text = args.currentDish.type

            btnReviews.setOnClickListener {
                showReviewDialog()
            }

            reviewListAdapter = ReviewDishAdapter(layoutInflater)
            rvReviews.initVerticalRecycler(reviewListAdapter)

            dishViewModel.rateDishliveData.observe(viewLifecycleOwner) { manageRateDishScreenState(it) }
            dishViewModel.getCommentList(args.currentDish.id)
            dishViewModel.commentListliveData.observe(viewLifecycleOwner) { manageCommentListScreenState(it) }
        }
    }

    private fun manageCommentListScreenState(state: BaseViewModel.ScreenState<DishViewModel.CommentListDishState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageComentListState(state.renderState)
            }
        }
    }

    private fun manageComentListState(state: DishViewModel.CommentListDishState) {
        hideProgressDialog()
        when(state){
            DishViewModel.CommentListDishState.Empty -> {
                binding.tvEmpty.visible()
            }
            is DishViewModel.CommentListDishState.SUCCESS -> {
                reviewList = state.commentList
                reviewListAdapter.updateList(state.commentList)
            }
            is DishViewModel.CommentListDishState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageRateDishScreenState(state: BaseViewModel.ScreenState<DishViewModel.RateDishState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageRateDishState(state.renderState)
            }
        }
    }

    private fun manageRateDishState(state: DishViewModel.RateDishState) {
        hideProgressDialog()
        when(state){
            is DishViewModel.RateDishState.SUCCESS -> {
                showToast("Se ha enviado su reseña")
            }
            is DishViewModel.RateDishState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun showReviewDialog() {
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
                            prefs.id?.toInt()?.let { it1 -> dishViewModel.rateDish(it1, args.currentDish.id, RateDishBody(commentRating, inputComment.text.toString())) }
                            reviewList += listOf(ReviewDish(0,commentRating.toFloat(), inputComment.text.toString()))
                            reviewListAdapter.updateList(reviewList)
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