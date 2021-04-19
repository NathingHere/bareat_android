package com.example.bareat_android.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentOnboardingBinding
import com.example.bareat_android.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

     override fun initializeBinding(): FragmentOnboardingBinding {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
         return binding
    }

    override fun initView() {

        btnRegister.setOnClickListener {
            navController?.navigate(OnBoardingFragmentDirections.routeToRegister())
        }
        btnLogin.setOnClickListener {
            navController?.navigate(OnBoardingFragmentDirections.routeToLogin())
        }

    }

    override fun setToolbar() {

    }


}