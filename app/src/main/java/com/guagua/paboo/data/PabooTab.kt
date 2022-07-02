package com.guagua.paboo.data

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.guagua.paboo.R
import com.guagua.paboo.presentation.navigation.Screen

enum class PabooTab(
    val screen: Screen,
    @StringRes val textResId: Int,
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int,
) {
    HOME(Screen.Home, R.string.tab_home, R.drawable.ic_home_selected, R.drawable.ic_home_unselected),
    CATEGORY(Screen.Category, R.string.tab_category, R.drawable.ic_category_selected, R.drawable.ic_category_unselected);
}