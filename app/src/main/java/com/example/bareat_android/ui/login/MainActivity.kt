package com.example.bareat_android.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.view.updatePadding
import androidx.navigation.NavDirections
import com.example.bareat_android.R
import com.example.bareat_android.databinding.ActivityMainBinding
import com.example.bareat_android.setup.extensions.gone
import com.example.bareat_android.setup.extensions.launchActivity
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseActivity
import com.example.bareat_android.ui.onboarding.OnBoardingActivity
import com.example.bareat_android.ui.profile.ProfileFragmentDirections.Companion.routeToSearch
import com.example.bareat_android.ui.search.SearchFragmentDirections.Companion.routeToHome
import com.example.bareat_android.ui.search.SearchFragmentDirections.Companion.routeToProfile

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        @JvmStatic
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private var isInsideBottomNav = true

    override fun initializeBinding(): ActivityMainBinding {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

        with(binding) {

            bareatToolbar.updatePadding(top = getStatusBarHeight())

            val menu = bottomNavigationView.menu
            val homeIcon = menu.findItem(R.id.homeFragment)
            val searchIcon = menu.findItem(R.id.searchFragment)
            val userIcon = menu.findItem(R.id.profileFragment)

            bottomNavigationView.itemIconTintList = null

            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        navigateToFragment(routeToHome())
                        homeIcon.setIcon(R.drawable.ic_home)
                        searchIcon.setIcon(R.drawable.ic_search_inactive)
                        userIcon.setIcon(R.drawable.ic_user_inactive)
                        true
                    }

                    R.id.searchFragment -> {
                        navigateToFragment(routeToSearch())
                        homeIcon.setIcon(R.drawable.ic_home_inactive)
                        searchIcon.setIcon(R.drawable.ic_search)
                        userIcon.setIcon(R.drawable.ic_user_inactive)
                        true
                    }

                    R.id.profileFragment -> {
                        navigateToFragment(routeToProfile())
                        homeIcon.setIcon(R.drawable.ic_home_inactive)
                        searchIcon.setIcon(R.drawable.ic_search_inactive)
                        userIcon.setIcon(R.drawable.ic_user)
                        true
                    }
                    else -> super.onContextItemSelected(item)
                }
            }

            navController?.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> {
                        showNavigationBottomMenu()
                        isInsideBottomNav = true
                        homeIcon.setIcon(R.drawable.ic_home)
                        searchIcon.setIcon(R.drawable.ic_search_inactive)
                        userIcon.setIcon(R.drawable.ic_user_inactive)
                    }
                    R.id.searchFragment -> {
                        showNavigationBottomMenu()
                        isInsideBottomNav = true
                    }
                    R.id.profileFragment -> {
                        showNavigationBottomMenu()
                        isInsideBottomNav = true
                    }
                    else -> {
                        hideNavigationBottomMenu()
                        isInsideBottomNav = false
                    }
                }
            }

        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    fun hideNavigationBottomMenu() {
        binding.bottomNavigationView.gone()
    }

    private fun showNavigationBottomMenu() {
        binding.bottomNavigationView.visible()
    }

    fun provideToolbar() = binding.bareatToolbar

    private fun navigateToFragment(action: NavDirections) {
        navController?.navigate(action)
    }

    fun hideLoading() {
        binding.progressBar.gone()
    }

    fun showLoading() {
        binding.progressBar.visible()
    }

    override fun onBackPressed() {
        if (isInsideBottomNav) finish() else super.onBackPressed()
    }

    fun routeToLogin() {
        launchActivity<OnBoardingActivity>(finish = true)
    }

}