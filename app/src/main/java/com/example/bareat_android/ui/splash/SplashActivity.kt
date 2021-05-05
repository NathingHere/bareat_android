package com.example.bareat_android.ui.splash

import android.os.CountDownTimer
import com.example.bareat_android.databinding.ActivitySplashBinding
import com.example.bareat_android.setup.extensions.launchActivity
import com.example.bareat_android.ui.base.BaseActivity
import com.example.bareat_android.ui.login.MainActivity
import com.example.bareat_android.ui.onboarding.OnBoardingActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initializeBinding(): ActivitySplashBinding {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

        val countdownTimer = object : CountDownTimer(1500, 500) {
            override fun onFinish() {
                when {
                    prefs.token.isNullOrEmpty() -> launchActivity<MainActivity>(finish = true)
                    prefs.token?.isNotEmpty() == true -> launchActivity<MainActivity>(finish = true)
                }
            }

            override fun onTick(p0: Long) {

            }
        }
        countdownTimer.start()

    }

}
