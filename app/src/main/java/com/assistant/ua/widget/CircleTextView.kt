package com.assistant.ua.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.assistant.ua.R

/** Created by qinbaoyuan on 2018/11/11
 */
class CircleTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    TextView(context, attributeSet, defStyleAttr) {

    private val mPaint = Paint()

    init {
        if (attributeSet != null) {
            val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextView)
            val bg = typeArray.getColor(R.styleable.CircleTextView_bg, Color.TRANSPARENT)
            mPaint.color = bg
            typeArray.recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(Math.max(measuredWidth, measuredHeight), Math.max(measuredWidth, measuredHeight))
    }

    override fun onDraw(canvas: Canvas) {
        val radius = width / 2
        canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), mPaint)

        canvas.clipRect(0, 0, width, width)

        super.onDraw(canvas)
    }
}