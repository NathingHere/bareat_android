package com.example.bareat_android.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import com.example.bareat_android.R
import com.example.bareat_android.setup.extensions.gone
import com.example.bareat_android.setup.extensions.visible
import kotlinx.android.synthetic.main.bareat_toolbar.view.*

class BareatToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    sealed class ToolbarItemMenu {
        object EmptyItem : ToolbarItemMenu()
        object HomeItemHot : ToolbarItemMenu()
        object HomeItemRated : ToolbarItemMenu()
        object HomeItemAlphabetically : ToolbarItemMenu()
        object ProfileItem : ToolbarItemMenu()
        object SearchItemEmpty : ToolbarItemMenu()
        object SearchItemRestaurant : ToolbarItemMenu()
        object SearchItemCafe : ToolbarItemMenu()
        object SearchItemBar : ToolbarItemMenu()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.bareat_toolbar, this, true)
        initToolbar(ToolbarItemMenu.EmptyItem)
    }

    fun initToolbar(itemMenu: ToolbarItemMenu) {
        when (itemMenu) {
            ToolbarItemMenu.EmptyItem -> setToolbarMenu(R.menu.empty_menu)
            ToolbarItemMenu.HomeItemHot -> setToolbarMenu(R.menu.home_menu_hot)
            ToolbarItemMenu.HomeItemRated -> setToolbarMenu(R.menu.home_menu_rated)
            ToolbarItemMenu.HomeItemAlphabetically -> setToolbarMenu(R.menu.home_menu_alphabetically)
            ToolbarItemMenu.ProfileItem -> setToolbarMenu(R.menu.profile_menu)
            ToolbarItemMenu.SearchItemEmpty -> setToolbarMenu(R.menu.search_menu_empty)
            ToolbarItemMenu.SearchItemRestaurant -> setToolbarMenu(R.menu.search_menu_restaurant)
            ToolbarItemMenu.SearchItemCafe -> setToolbarMenu(R.menu.search_menu_cafe)
            ToolbarItemMenu.SearchItemBar -> setToolbarMenu(R.menu.search_menu_bar)
        }
    }

    private fun setToolbarMenu(@MenuRes menu: Int) {
        clearMenu()
        clearNavigationIcon()
        inflateMenu(menu)
    }

    private fun clearMenu() {
        menu.clear()
    }

    fun setOnMenuItemClick(
        onCancelClick: () -> Unit = {},
        onDoneClick: () -> Unit = {},
        onPopularityClick: () -> Unit = {},
        onRatedClick: () -> Unit = {},
        onAlphabeticalClick: () -> Unit = {},
        onSettingsClick: () -> Unit = {},
        onNothingClick: () -> Unit = {},
        onRestaurantClick: () -> Unit = {},
        onBarClick: () -> Unit = {},
        onCafeClick: () -> Unit = {}
    ) {

        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popularity -> {
                    onPopularityClick.invoke()
                    true
                }
                R.id.best_rated -> {
                    onRatedClick.invoke()
                    true
                }
                R.id.alphabetically -> {
                    onAlphabeticalClick.invoke()
                    true
                }
                R.id.settings_item -> {
                    onSettingsClick.invoke()
                    true
                }
                R.id.nothing -> {
                    onNothingClick.invoke()
                    true
                }
                R.id.bar -> {
                    onBarClick.invoke()
                    true
                }
                R.id.restaurant -> {
                    onRestaurantClick.invoke()
                    true
                }
                R.id.cafe -> {
                    onCafeClick.invoke()
                    true
                }
                else -> true
            }
        }
        bareatToolbarCancel.setOnClickListener { onCancelClick.invoke() }
        bareatToolbarDone.setOnClickListener { onDoneClick.invoke() }
    }

    fun setBigToolbarTitle(title: String) {
        bareatToolbarTitle.visible()
        bareatToolbarTitle.text = title
        bareatToolbarTitleSmall.gone()
    }

    fun setSmallToolbarTitle(title: String) {
        bareatToolbarTitleSmall.visible()
        bareatToolbarTitleSmall.text = title
        bareatToolbarTitle.gone()
    }

    fun showDoneCancelButtons() {
        bareatToolbarCancel.visible()
        bareatToolbarDone.visible()
    }

    fun hideDoneCancelButtons() {
        bareatToolbarCancel.gone()
        bareatToolbarDone.gone()
    }

    fun setNavIcon(@DrawableRes icon: Int, onClick: () -> Unit) {
        setNavigationIcon(icon)
        setNavigationOnClickListener { onClick.invoke() }
    }

    private fun clearNavigationIcon() {
        navigationIcon = null
    }
}