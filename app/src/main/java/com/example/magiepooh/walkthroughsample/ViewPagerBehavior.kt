package com.example.magiepooh.walkthroughsample

import androidx.viewpager.widget.ViewPager

interface ViewPagerBehavior {
  val onPageOffsetChanged: (Float) -> Unit

  /** [ViewPager.onPageScrolled]と繋がってView用のoffSetを計算する */
  fun onPageScrolled(viewPosition: Int): (Int, Float, Int) -> Unit =
    { pagerPosition, positionOffset, _ ->

      val offSet: Float = viewPosition - pagerPosition - positionOffset
      onPageOffsetChanged.invoke(when {
        offSet >= 1f -> 1f
        offSet <= -1f -> -1f
        else -> offSet
      })
    }
}
