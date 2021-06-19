package com.example.bareat_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bareat_android.databinding.ReviewItemviewBinding
import com.example.bareat_android.databinding.ReviewItemviewUserBinding
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.restaurant.RestaurantFragment
import com.example.data.ReviewDish
import com.example.data.ReviewRestaurant

class ReviewUserDishAdapter(private val layoutInflater: LayoutInflater, private val onClick: (ReviewDishActionItem) -> Unit) : BaseRecyclerView<ReviewDish, ReviewUserDishAdapter.Holder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ReviewUserDishAdapter.Holder =
        Holder(ReviewItemviewUserBinding.inflate(layoutInflater, parent, false))

    inner class Holder(private val reviewItemviewBinding: ReviewItemviewUserBinding) : BaseViewHolder<ReviewDish>(reviewItemviewBinding) {

        override fun bindData(item: ReviewDish) {
            with(reviewItemviewBinding) {
                tvReview.text = item.comment
                tvName.text = item.dish?.name ?: ""
                val rating = item.rating?.div(2f)
                rating?.let {
                    ratingBar.rating = it
                }

                root.setOnClickListener {
                    item.dish?.let { it1 -> ReviewDishActionItem.Route(it1) }?.let { it2 ->
                        onClick.invoke(
                            it2
                        )
                    }
                }

                btnEdit.setOnClickListener {
                    onClick.invoke(ReviewDishActionItem.Edit(item))
                }

                btnDelete.setOnClickListener {
                    onClick.invoke(ReviewDishActionItem.Delete(item))
                }

            }
        }
    }

}