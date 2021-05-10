package com.example.bareat_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bareat_android.databinding.ReviewItemviewBinding
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.restaurant.RestaurantFragment
import com.example.data.ReviewRestaurant

class ReviewAdapter(private val layoutInflater: LayoutInflater) : BaseRecyclerView<ReviewRestaurant, ReviewAdapter.Holder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.Holder =
        Holder(ReviewItemviewBinding.inflate(layoutInflater, parent, false))

    inner class Holder(private val reviewItemviewBinding: ReviewItemviewBinding) : BaseViewHolder<ReviewRestaurant>(reviewItemviewBinding) {

        override fun bindData(item: ReviewRestaurant) {
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