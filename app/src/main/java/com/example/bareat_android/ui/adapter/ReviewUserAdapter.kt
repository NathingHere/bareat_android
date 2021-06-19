package com.example.bareat_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bareat_android.databinding.ReviewItemviewBinding
import com.example.bareat_android.databinding.ReviewItemviewUserBinding
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.restaurant.RestaurantFragment
import com.example.data.ReviewRestaurant

class ReviewUserAdapter(private val layoutInflater: LayoutInflater, private val onClick: (ReviewRestaurantActionItem) -> Unit) : BaseRecyclerView<ReviewRestaurant, ReviewUserAdapter.Holder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ReviewUserAdapter.Holder =
        Holder(ReviewItemviewUserBinding.inflate(layoutInflater, parent, false))

    inner class Holder(private val reviewItemviewBinding: ReviewItemviewUserBinding) : BaseViewHolder<ReviewRestaurant>(reviewItemviewBinding) {

        override fun bindData(item: ReviewRestaurant) {
            with(reviewItemviewBinding) {
                tvReview.text = item.comment
                tvName.text = item.restaurant?.name ?: ""
                val rating = item.rating?.div(2f)
                rating?.let {
                    ratingBar.rating = it
                }

                root.setOnClickListener {
                    item.restaurant?.let { it1 ->
                        ReviewRestaurantActionItem.Route(
                            it1
                        )
                    }?.let { it2 -> onClick.invoke(it2) }
                }

                btnEdit.setOnClickListener {
                    onClick.invoke(ReviewRestaurantActionItem.Edit(item))
                }

                btnDelete.setOnClickListener {
                    onClick.invoke(ReviewRestaurantActionItem.Delete(item))
                }

            }
        }
    }

}