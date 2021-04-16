package com.example.bareat_android.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentOnboardingBinding
import com.example.bareat_android.ui.base.BaseFragment


class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

     override fun initializeBinding(): FragmentOnboardingBinding {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
         return binding
    }

    override fun initView() {

    }

    override fun setToolbar() {

    }


}