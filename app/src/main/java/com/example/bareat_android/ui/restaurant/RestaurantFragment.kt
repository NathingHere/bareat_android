package com.example.bareat_android.ui.restaurant

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
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
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.bareat_android.databinding.ViewpagerImageBinding
import com.example.bareat_android.setup.extensions.logD
import com.example.bareat_android.ui.adapter.ReviewAdapter
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.login.MainActivity
import com.example.data.Dish
import com.example.data.Image
import com.example.data.Restaurant
import com.example.data.ReviewRestaurant
import com.example.data.tosend.RateRestaurantBody
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.dialog_rating.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : BaseFragment<FragmentRestaurantBinding>(), OnMapReadyCallback {

    private val args by navArgs<RestaurantFragmentArgs>()

    private val restaurantViewModel by viewModel<RestaurantViewModel>()

    private val dishListAdapter = DishListAdapter { onDishClick(it) }
    private lateinit var reviewListAdapter : ReviewAdapter
    private val imageListAdapter = ImageListAdapter()

    private var restaurantList = listOf<ReviewRestaurant>()

    private lateinit var reviewList:List<ReviewRestaurant>

    private lateinit var dialog: Dialog

    private var isReviewFinish = false
    private var isThisFinish = false
    private var isImageFinish = false

    private var commentRating = 5

    private lateinit var googleMap: GoogleMap

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)


    }

    override fun initView() {
        restaurantViewModel.init()

        with (binding) {
            tvName.text = args.currentRestaurant.name
            val rating = args.currentRestaurant.rating?.div(2f)
            rating?.let {
                ratingBar.rating = it
            }
            tvDescription.text = args.currentRestaurant.desc
            if (rating != null)
                tvScore.text = getString(R.string.score, rating)
            else
                tvScore.text = getString(R.string.score, 0F)
            tvType.text = args.currentRestaurant.type
            tvLocation.text = args.currentRestaurant.address
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

            reviewListAdapter = ReviewAdapter(layoutInflater)
            vpRestaurant.adapter = imageListAdapter
            dotsIndicator.setViewPager2(vpRestaurant)
            rvDishes.initHorizontalRecycler(dishListAdapter)
            rvReviews.initVerticalRecycler(reviewListAdapter)

            btnBook.setOnClickListener {
                prefs.restaurantId = args.currentRestaurant.id.toString()
                navController?.navigate(R.id.route_to_book)
            }

            Log.e(TAG, "COORDENADAS: " + args.currentRestaurant.latitude.toString() + " " + args.currentRestaurant.longitude.toString())

        }

        restaurantViewModel.getImageList(args.currentRestaurant.id)
        restaurantViewModel.imageListData.observe(viewLifecycleOwner) { manageImageScreenState(it) }

        restaurantViewModel.getDishList(args.currentRestaurant.id)
        restaurantViewModel.dishListData.observe(viewLifecycleOwner) { manageDishesScreenState(it) }

        restaurantViewModel.getReviewList(args.currentRestaurant.id)
        restaurantViewModel.reviewListData.observe(viewLifecycleOwner) { manageReviewsScreenState(it) }

        restaurantViewModel.rateRestaurantListData.observe(viewLifecycleOwner) { manageRateRestaurantScreenState(it) }
    }

    private fun manageRateRestaurantScreenState(state: BaseViewModel.ScreenState<RestaurantViewModel.RateRestaurantState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageRateRestaurantState(state.renderState)
            }
        }
    }

    private fun manageRateRestaurantState(state: RestaurantViewModel.RateRestaurantState) {
        hideProgressDialog()
        when(state){
            is RestaurantViewModel.RateRestaurantState.SUCCESS -> {
                showToast("Se ha enviado su reseña")
            }
            is RestaurantViewModel.RateRestaurantState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageImageScreenState(state: BaseViewModel.ScreenState<RestaurantViewModel.ImageState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageImageState(state.renderState)
            }
        }
    }

    private fun manageImageState(state: RestaurantViewModel.ImageState) {
        isImageFinish = true
        isCallFinished()
        when(state){
            is RestaurantViewModel.ImageState.SUCCESS -> {

                imageListAdapter.updateList(state.imageList.subList(0, 5))
            }
            is RestaurantViewModel.ImageState.ERROR -> showToast(state.errorMessage)
        }
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
            is RestaurantViewModel.ReviewState.SUCCESS -> {
                reviewList = state.reviewRestaurantList
                reviewListAdapter.updateList(state.reviewRestaurantList)
            }
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
                    val rating = item.rating?.div(2f)
                    rating?.let {
                        ratingBar.rating = it
                    }
                    tvPrice.text = getString(R.string.price, item.price)

                    card.setOnClickListener {
                        onClick.invoke(item)
                    }

                }
            }

        }

    }

    inner class ImageListAdapter : BaseRecyclerView<Image, ImageListAdapter.Holder>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.Holder = Holder(
            ViewpagerImageBinding.inflate(layoutInflater, parent, false))

        inner class Holder(private val viewpagerImageBinding: ViewpagerImageBinding) : BaseViewHolder<Image>(viewpagerImageBinding) {

            override fun bindData(item: Image) {
                with(viewpagerImageBinding) {
                    ivViewPager.load(item.url)
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
                    ratingBarReview.setOnRatingBarChangeListener { _, rating, _ ->
                        commentRating = (rating * 2).toInt()
                    }
                    commentYes.setOnClickListener {
                        if(inputComment.text.isNotEmpty()) {
                            prefs.id?.toInt()?.let { it1 -> restaurantViewModel.rateRestaurant(it1, args.currentRestaurant.id, RateRestaurantBody(commentRating, inputComment.text.toString())) }
                            reviewList += listOf(ReviewRestaurant(0,commentRating.toFloat(), inputComment.text.toString()))
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

    private fun onDishClick(dish: Dish) {
        navController?.navigate(RestaurantFragmentDirections.routeToDish(dish))
    }

    private fun isCallFinished() {
        if(isReviewFinish && isThisFinish && isImageFinish) hideProgressDialog()
    }

    override fun onMapReady(map: GoogleMap) {
        map?.let {
            googleMap = it
            val latlng = args.currentRestaurant.latitude?.let { it1 -> args.currentRestaurant.longitude?.let { it2 ->
                LatLng(it1,
                    it2
                )
            } }
            if (latlng != null) {
                it.addMarker(MarkerOptions().position(latlng).title(args.currentRestaurant.name))
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 20F))
            }
        }
    }

}