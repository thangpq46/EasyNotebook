package com.qt46.easynotebook.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomBarItem(val screen: Screen,@DrawableRes val iconId:Int,@StringRes val labelID: Int)
