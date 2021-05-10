package com.example.bareat_android.ui.dish

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentDishBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.restaurant.RestaurantFragmentArgs
import kotlinx.android.synthetic.main.dialog_rating.*

class DishFragment : BaseFragment<FragmentDishBinding>() {

    private val args by navArgs<DishFragmentArgs>()

    private lateinit var dialog: Dialog

    override fun initializeBinding(): FragmentDishBinding {
        binding = FragmentDishBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.EmptyItem)
            visible()
            hideDoneCancelButtons()
            setSmallToolbarTitle(args.currentDish.name ?: "")
            setNavIcon(R.drawable.ic_left_arrow) {
                homeActivity().onBackPressed()
            }
        }
    }

    override fun initView() {

        with(binding) {
            tvName.text = args.currentDish.name
            args.currentDish.rating?.let {
                ratingBar.rating = it
            }
            tvDescription.text = args.currentDish.desc
            tvScore.text = getString(R.string.score, args.currentDish.rating)
            tvType.text = args.currentDish.type

            btnReviews.setOnClickListener {
                showReviewDialog()
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
        TODO("Not yet implemented")
    }

}