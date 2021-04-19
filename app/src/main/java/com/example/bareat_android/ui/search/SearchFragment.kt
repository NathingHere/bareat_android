package com.example.bareat_android.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentSearchBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.customview.BareatToolbar


class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun initializeBinding(): FragmentSearchBinding {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.EmptyItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_search))
        }
    }

    override fun initView() {

    }

}