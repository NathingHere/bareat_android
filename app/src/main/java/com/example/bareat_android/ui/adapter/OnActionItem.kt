package com.example.bareat_android.ui.adapter

import com.example.data.*

sealed class ReviewRestaurantActionItem {
    data class Route(val restaurant: Restaurant) : ReviewRestaurantActionItem()
    data class Delete(val review: ReviewRestaurant) : ReviewRestaurantActionItem()
    data class Edit(val review: ReviewRestaurant) : ReviewRestaurantActionItem()
}

sealed class ReviewDishActionItem {
    data class Route(val dish: Dish) : ReviewDishActionItem()
    data class Delete(val review: ReviewDish) : ReviewDishActionItem()
    data class Edit(val review: ReviewDish) : ReviewDishActionItem()
}

sealed class BookActionItem {
    data class Route(val restaurant: Restaurant) : BookActionItem()
    data class Delete(val book: DataBook) : BookActionItem()
}