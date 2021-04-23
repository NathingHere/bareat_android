package com.example.bareat_android.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentProfileBinding
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.customview.BareatToolbar


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initializeBinding(): FragmentProfileBinding {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.ProfileItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_profile))
            setOnMenuItemClick (
                    onSettingsClick = {})
        }
    }

    override fun initView() {

    }

}