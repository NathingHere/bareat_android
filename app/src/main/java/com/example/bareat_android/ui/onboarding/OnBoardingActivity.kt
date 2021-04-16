package com.example.bareat_android.ui.onboarding

import com.example.bareat_android.databinding.ActivityOnboardingBinding
import com.example.bareat_android.ui.base.BaseActivity

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(){

    override fun initializeBinding(): ActivityOnboardingBinding {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

}