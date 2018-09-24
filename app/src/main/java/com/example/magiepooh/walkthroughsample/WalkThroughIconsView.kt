package com.example.magiepooh.walkthroughsample

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.example.magiepooh.walkthroughsample.databinding.LayoutWalkThroughIconsBinding

class WalkThroughIconsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    companion object {
        private const val DEFAULT_PARALLAX_MULTIPLIER = 0.05f
    }

    private val binding: LayoutWalkThroughIconsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_walk_through_icons, this, true)

    private val displayWidth: Int = resources.displayMetrics.widthPixels
    private val parallaxMultiplier: Float

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WalkThroughIconsView)
        parallaxMultiplier = typedArray.getFloat(R.styleable.WalkThroughIconsView_parallax_multiplier,
                DEFAULT_PARALLAX_MULTIPLIER)
        typedArray.recycle()
    }

    fun startScaleAnim() {
        arrayOf(binding.icBike).forEachIndexed { index, image ->

            val delay = 100L + (index * 70)
            AnimatorSet().apply {
                duration = 300
                startDelay = delay
                playTogether(
                        ObjectAnimator.ofFloat(image, View.SCALE_X, 0f, 1f),
                        ObjectAnimator.ofFloat(image, View.SCALE_Y, 0f, 1f)
                )
            }.start()
        }
    }

    val onPageScrolled: (Int, Float, Int) -> Unit = { position, positionOffset, _ ->
        translationX = -(displayWidth * (position + positionOffset) * parallaxMultiplier)
    }
}
