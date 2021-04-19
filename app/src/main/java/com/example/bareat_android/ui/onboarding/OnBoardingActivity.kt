package com.example.bareat_android.ui.onboarding

import android.content.Context
import android.content.Intent
import com.example.bareat_android.databinding.ActivityOnboardingBinding
import com.example.bareat_android.ui.base.BaseActivity
import com.example.bareat_android.ui.login.MainActivity

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(){

    override fun initializeBinding(): ActivityOnboardingBinding {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    companion object {
        @JvmStatic
        fun intent(context: Context) =
                Intent(context, MainActivity::class.java)
    }
}