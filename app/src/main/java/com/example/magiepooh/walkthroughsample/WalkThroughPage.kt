package com.example.magiepooh.walkthroughsample

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

enum class WalkThroughPage(@StringRes val title: Int, @ColorRes val color: Int) {
    DEVICE_1(R.string.title_1, R.color.image_1),
    DEVICE_2(R.string.title_2, R.color.image_2),
    DEVICE_3(R.string.title_3, R.color.image_3)
}
