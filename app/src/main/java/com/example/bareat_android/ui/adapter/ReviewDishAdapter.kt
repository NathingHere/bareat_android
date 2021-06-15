package com.example.bareat_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bareat_android.databinding.ReviewItemviewBinding
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.restaurant.RestaurantFragment
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant

class ReviewDishAdapter(private val layoutInflater: LayoutInflater) : BaseRecyclerView<ReviewDish, ReviewDishAdapter.Holder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ReviewDishAdapter.Holder =
        Holder(ReviewItemviewBinding.inflate(layoutInflater, parent, false))

    inner class Holder(private val reviewItemviewBinding: ReviewItemviewBinding) : BaseViewHolder<ReviewDish>(reviewItemviewBinding) {

        override fun bindData(item: ReviewDish) {
            with(reviewItemviewBinding) {
                //tvName.text = item.name
                tvReview.text = item.comment
                val rating = item.rating?.div(2f)
                rating?.let {
                    ratingBar.rating = it
                }

            }
        }
    }

}