package com.example.magiepooh.walkthroughsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class ParallaxViewPagerBackgroundView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : View(context, attrs, defStyle) {

    companion object {
        private const val DEFAULT_PARALLAX_DIVISOR = 0.73f
    }

    private val displayHeight: Float = resources.displayMetrics.heightPixels.toFloat()
    private val displayWidth: Float = resources.displayMetrics.widthPixels.toFloat()

    private val rectNow = RectF(0f, 0f, displayWidth, displayHeight)
    private val rectNext = RectF()

    private var currentPosition: WalkThroughPage = WalkThroughPage.DEVICE_1
    private var scrollState: ScrollState = ScrollState.STOP

    private val parallaxDivisor: Float

    private val background1 = ContextCompat.getColor(context, R.color.background_1)
    private val background2 = ContextCompat.getColor(context, R.color.background_2)
    private val background3 = ContextCompat.getColor(context, R.color.background_3)

    private val backgroundPaintNow = Paint().apply {
        isAntiAlias = true
        color = background1
    }
    private val backgroundPaintNext = Paint().apply {
        isAntiAlias = true
        color = background2
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ParallaxViewPagerBackgroundView)

        parallaxDivisor = typedArray.getFloat(
                R.styleable.ParallaxViewPagerBackgroundView_parallax_divisor, DEFAULT_PARALLAX_DIVISOR)
        typedArray.recycle()
    }

    val onPageScrolled: (Int, Float, Int) -> Unit = { position, positionOffset, positionOffsetPixels ->

        if (positionOffsetPixels == 0 || currentPosition.ordinal != position) {
            scrollState = if (positionOffset < 0.5f) ScrollState.FORWARD else ScrollState.BACKWARD
        }

        if (currentPosition.ordinal != position) {
            currentPosition = WalkThroughPage.values()[position]
            when (currentPosition) {
                WalkThroughPage.DEVICE_1 -> {
                    backgroundPaintNow.color = background1
                    backgroundPaintNext.color = background2
                }
                WalkThroughPage.DEVICE_2 -> {
                    backgroundPaintNow.color = background2
                    backgroundPaintNext.color = background3
                }
                WalkThroughPage.DEVICE_3 -> {
                    backgroundPaintNow.color = background3
                    backgroundPaintNext.color = background1
                }
            }
        }

        rectNext.set(calcLeftX(positionOffset), 0f, displayWidth, displayHeight)

        invalidate()
    }

    private fun calcLeftX(positionOffset: Float): Float {
        return if (scrollState == ScrollState.FORWARD) {
            displayWidth - (displayWidth * (positionOffset / parallaxDivisor))
        } else {
            displayWidth * ((1 - positionOffset) / parallaxDivisor)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(rectNow, backgroundPaintNow)
        canvas.drawRect(rectNext, backgroundPaintNext)
    }

    enum class ScrollState {
        STOP,
        FORWARD,
        BACKWARD
    }
}
