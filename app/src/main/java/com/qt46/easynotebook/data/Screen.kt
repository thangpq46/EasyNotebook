package com.qt46.easynotebook.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.qt46.easynotebook.R

sealed class Screen(val route: String, @StringRes val title: Int, @DrawableRes val iconId: Int) {
    data object AddNote : Screen("add", R.string.app_name, R.drawable.ic_launcher_foreground)
    data object Notes : Screen("notes", R.string.app_name, R.drawable.ic_launcher_foreground)
    data object UpdateNote : Screen("update", R.string.app_name, R.drawable.ic_launcher_foreground)
}