package com.example.magiepooh.walkthroughsample

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class WalkThroughPagerAdapter : PagerAdapter() {

    // ウォークスルーでは、Pagerの描画範囲を超えてParallaxを表現するためPagerにはダミーのViewを渡す
    override fun instantiateItem(container: ViewGroup, position: Int): Any =
            View(container.context).apply { container.addView(this) }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int = WalkThroughPage.values().size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
}
