package com.example.bareat_android.ui.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentRestaurantBinding
import com.example.bareat_android.ui.base.BaseFragment
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog

class RestaurantFragment : BaseFragment<FragmentRestaurantBinding>() {

    override fun initializeBinding(): FragmentRestaurantBinding {
        binding = FragmentRestaurantBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {

    }

    override fun initView() {
        with (binding) {

            btnBook.setOnClickListener {
                val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
                val sheetView = layoutInflater.inflate(R.layout.fragment_book, null)
                mBottomSheetDialog.setContentView(sheetView)
                mBottomSheetDialog.show()
            }
        }

    }

}