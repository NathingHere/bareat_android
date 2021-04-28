package com.example.bareat_android.ui.dish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentDishBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.restaurant.RestaurantFragmentArgs

class DishFragment : BaseFragment<FragmentDishBinding>() {

    override fun initializeBinding(): FragmentDishBinding {
        binding = FragmentDishBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {

    }

    override fun initView() {

        with(binding) {

        }
    }

}