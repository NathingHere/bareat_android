package com.example.bareat_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.bareat_android.databinding.RestaurantItemviewBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.data.Restaurant
import java.util.*

class RestaurantAdapter(
        private val layoutInflater: LayoutInflater,
        private val onClick: (Restaurant) -> Unit
) : BaseRecyclerView<Restaurant, RestaurantAdapter.Holder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapter.Holder =
        Holder(RestaurantItemviewBinding.inflate(layoutInflater, parent, false))

    inner class Holder(private val restaurantItemviewBinding: RestaurantItemviewBinding) : BaseViewHolder<Restaurant>(restaurantItemviewBinding) {

        override fun bindData(item: Restaurant) {
            with (restaurantItemviewBinding) {
                ivRestaurant.load(item.cover)
                tvName.text = item.name
                if (item.isPremium == true) tvAdd.visible()
                tvDetails.text = item.type
                ratingBar.numStars = item.rating!!
            }
        }

    }

}