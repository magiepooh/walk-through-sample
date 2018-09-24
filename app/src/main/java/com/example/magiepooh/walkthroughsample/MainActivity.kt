package com.example.magiepooh.walkthroughsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.magiepooh.walkthroughsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)


        val device1View = DeviceView(this).apply { initView(WalkThroughPage.DEVICE_1) }
        val device2View = DeviceView(this).apply { initView(WalkThroughPage.DEVICE_2) }
        val device3View = DeviceView(this).apply { initView(WalkThroughPage.DEVICE_3) }

        binding.walkThroughViewContainer.run {
            addView(device1View)
            addView(device2View)
            addView(device3View)
        }

        binding.viewpager.run {
            adapter = WalkThroughPagerAdapter()
            addOnPageChangeListener(onPageScrolled = device1View.onPageScrolled(WalkThroughPage.DEVICE_1.ordinal))
            addOnPageChangeListener(onPageScrolled = device2View.onPageScrolled(WalkThroughPage.DEVICE_2.ordinal))
            addOnPageChangeListener(onPageScrolled = device3View.onPageScrolled(WalkThroughPage.DEVICE_3.ordinal))
            addOnPageChangeListener(onPageScrolled = binding.backgroundView.onPageScrolled)
            addOnPageChangeListener(onPageScrolled = binding.iconsLayout.onPageScrolled)
        }
    }
}

private inline fun ViewPager.addOnPageChangeListener(
        crossinline onPageScrollStateChanged: (Int) -> Unit = {},
        crossinline onPageScrolled: (Int, Float, Int) -> Unit = { _, _, _ -> },
        crossinline onPageSelected: (Int) -> Unit = {}
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected(position)
        }
    })
}
