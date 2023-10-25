package com.qt46.easynotebook.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.qt46.easynotebook.notes.SortBy

data class SortType(val type: SortBy, @StringRes val nameId: Int, @DrawableRes val iconID: Int)
