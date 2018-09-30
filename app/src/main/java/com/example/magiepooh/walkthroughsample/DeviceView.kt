package com.example.magiepooh.walkthroughsample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import com.example.magiepooh.walkthroughsample.databinding.LayoutWalkThroughWithDeviceBinding

class DeviceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle), ViewPagerBehavior {

    companion object {
        private const val PARALLAX_DIVISOR_TITLE = 0.62f
        private const val PARALLAX_DIVISOR_DEVICE = 0.89f
        private const val DEVICE_MAX_ROTATION = 18f
        /**
         * @akono 氏のPrincipleより 48(Y) / 522(device height)
         * */
        private const val MIN_HEIGHT_RATIO = 0.091954f
    }

    private val binding: LayoutWalkThroughWithDeviceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_walk_through_with_device, this, true)

    private val displayWidth: Float = resources.displayMetrics.widthPixels.toFloat()

    private var titleMaxTranslationX: Float = 0f
    private var deviceMaxTranslationX: Float = 0f
    private var deviceMaxTranslationY: Float = 0f

    init {
        doOnPreDraw {
            titleMaxTranslationX = calcMaxTranslationX(binding.title.width)

            val radians = Math.toRadians(DEVICE_MAX_ROTATION.toDouble())
            // 傾いたdevice画像の横幅計算
            val rotationWidth = (binding.device.width * Math.cos(
                    radians) + binding.device.height * Math.sin(radians)).toInt()
            deviceMaxTranslationX = calcMaxTranslationX(rotationWidth)
            deviceMaxTranslationY = binding.device.height * MIN_HEIGHT_RATIO
        }
    }

    fun initView(page: WalkThroughPage) {
        binding.title.setText(page.title)
        binding.device.setImageResource(page.color)
    }

    override val onPageOffsetChanged: (Float) -> Unit = { offSet ->
        if (!isLaidOut && offSet != 0f) {
            visibility = View.INVISIBLE
        } else {
            if (visibility != View.VISIBLE) visibility = View.VISIBLE
            binding.title.translationX = titleMaxTranslationX * (offSet / PARALLAX_DIVISOR_TITLE)
            binding.device.translationX = deviceMaxTranslationX * (offSet / PARALLAX_DIVISOR_DEVICE)
            binding.device.rotation = DEVICE_MAX_ROTATION * (offSet / PARALLAX_DIVISOR_DEVICE)
            binding.device.translationY = Math.abs(
                    deviceMaxTranslationY * (offSet / PARALLAX_DIVISOR_DEVICE))
        }
    }

    private fun calcMaxTranslationX(width: Int): Float = width + ((displayWidth - width) / 2)
}
