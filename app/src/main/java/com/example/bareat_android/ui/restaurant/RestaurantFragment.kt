package com.example.bareat_android.ui.restaurant

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentRestaurantBinding
import com.example.bareat_android.ui.base.BaseFragment
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.bareat_android.databinding.DishItemviewBinding
import com.example.bareat_android.databinding.ReviewItemviewBinding
import com.example.bareat_android.setup.extensions.initHorizontalRecycler
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.data.Dish
import com.example.data.Review
import kotlinx.android.synthetic.main.dialog_rating.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : BaseFragment<FragmentRestaurantBinding>() {

    private val args by navArgs<RestaurantFragmentArgs>()

    private val restaurantViewModel by viewModel<RestaurantViewModel>()

    private val dishListAdapter = DishListAdapter { onDishClick(it) }
    private val reviewListAdapter = ReviewListAdapter()

    private lateinit var dialog: Dialog

    private var isReviewFinish = false
    private var isThisFinish = false

    override fun initializeBinding(): FragmentRestaurantBinding {
        binding = FragmentRestaurantBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.EmptyItem)
            visible()
            hideDoneCancelButtons()
            setSmallToolbarTitle(args.currentRestaurant.name ?: "")
            setNavIcon(R.drawable.ic_left_arrow) {
                homeActivity().onBackPressed()
            }
        }
    }

    override fun initView() {
        restaurantViewModel.init()

        with (binding) {

            tvName.text = args.currentRestaurant.name
            args.currentRestaurant.rating?.let {
                ratingBar.rating = it
            }
            tvDescription.text = args.currentRestaurant.desc
            tvScore.text = getString(R.string.score, args.currentRestaurant.rating)
            tvType.text = args.currentRestaurant.type
            btnEmail.setOnClickListener {
                val mIntent = Intent(Intent.ACTION_SENDTO).apply {
                    val mailto = "mailto:" + args.currentRestaurant.email
                    data = Uri.parse(mailto)
                }
                startActivity(mIntent)
            }
            btnPhone.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + args.currentRestaurant.phone)
                startActivity(dialIntent)
            }
            btnReviews.setOnClickListener {
                showReviewDialog()
            }


            rvDishes.initHorizontalRecycler(dishListAdapter)
            rvReviews.initVerticalRecycler(reviewListAdapter)

            btnBook.setOnClickListener {
                val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
                val sheetView = layoutInflater.inflate(R.layout.fragment_book, null)
                mBottomSheetDialog.setContentView(sheetView)
                mBottomSheetDialog.show()
            }
        }

        restaurantViewModel.getDishList(args.currentRestaurant.id)
        restaurantViewModel.dishListData.observe(viewLifecycleOwner) { manageDishesScreenState(it) }

        restaurantViewModel.getReviewList(args.currentRestaurant.id)
        restaurantViewModel.reviewListData.observe(viewLifecycleOwner) { manageReviewsScreenState(it) }

    }

    private fun manageReviewsScreenState(state: BaseViewModel.ScreenState<RestaurantViewModel.ReviewState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageReviewState(state.renderState)
            }
        }
    }

    private fun manageReviewState(state: RestaurantViewModel.ReviewState) {
        isReviewFinish = true
        isCallFinished()
        when(state) {
            RestaurantViewModel.ReviewState.Empty -> {
                binding.tvEmpty.visible()
            }
            is RestaurantViewModel.ReviewState.SUCCESS -> reviewListAdapter.updateList(state.reviewList)
            is RestaurantViewModel.ReviewState.ERROR -> showToast(state.errorMessage)
        }

    }

    private fun manageDishesScreenState(state: BaseViewModel.ScreenState<RestaurantViewModel.DishState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageDishState(state.renderState)
            }
        }
    }

    private fun manageDishState(state: RestaurantViewModel.DishState) {
        isThisFinish = true
        isCallFinished()
        when(state){
            is RestaurantViewModel.DishState.SUCCESS -> dishListAdapter.updateList(state.dishList)
            is RestaurantViewModel.DishState.ERROR -> showToast(state.errorMessage)
        }
    }

    inner class DishListAdapter(private val onClick: (Dish) -> Unit) : BaseRecyclerView<Dish, DishListAdapter.Holder>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): DishListAdapter.Holder =
                Holder(DishItemviewBinding.inflate(layoutInflater, parent, false))

        inner class Holder(private val dishItemviewBinding: DishItemviewBinding) : BaseViewHolder<Dish>(dishItemviewBinding) {

            override fun bindData(item: Dish) {
                with(dishItemviewBinding) {
                    ivDish.load(item.cover)
                    tvName.text = item.name
                    item.rating?.let {
                        ratingBar.rating = it
                    }
                    tvPrice.text = getString(R.string.price, item.price)

                    root.setOnClickListener {
                        onClick.invoke(item)
                    }

                }
            }

        }

    }

    inner class ReviewListAdapter : BaseRecyclerView<Review, ReviewListAdapter.Holder>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): ReviewListAdapter.Holder =
                Holder(ReviewItemviewBinding.inflate(layoutInflater, parent, false))

        inner class Holder(private val reviewItemviewBinding: ReviewItemviewBinding) : BaseViewHolder<Review>(reviewItemviewBinding) {

            override fun bindData(item: Review) {
                with(reviewItemviewBinding) {
                    tvName.text = item.name
                    tvReview.text = item.comment
                    item.rating?.let {
                        ratingBar.rating = it
                    }

                }
            }
        }

    }

    private fun showReviewDialog() {
        context?.let { it ->
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_rating)

                with(binding) {
                    //Mandar datos
                    commentYes.setOnClickListener { comment() }
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

    private fun comment() {

    }

    private fun onDishClick(dish: Dish) {
        TODO("Not yet implemented")
    }

    private fun isCallFinished() {
        if(isReviewFinish && isThisFinish) hideProgressDialog()
    }

}