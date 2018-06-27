package com.bucai.torch.util.uiutils

import android.content.Context
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by zia on 2018/6/10.
 */
class ClassifyGroup : ViewGroup {

    private val TAG = javaClass.simpleName

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    public fun addStrings(strings: List<String>, column: Int, @DrawableRes id: Int, textSize: Float, @ColorInt textColor: Int) {
        if (strings.isEmpty()) return
        val itemViews = strings.map {
            val textView = TextView(context)
            textView.text = it
            textView.textSize = textSize
            Log.d("row", "before:${textView.textSize}")
            textView.background = resources.getDrawable(id)
            textView.setTextColor(textColor)
            textView.gravity = Gravity.CENTER
            textView.includeFontPadding = true
            textView
        }
        addItems(itemViews, column)
    }

    public fun addItems(itemViews: List<TextView>, column: Int) {
        if (column <= 0 || itemViews.isEmpty()) {
            Log.d(TAG, "column <= 0 || itemViews.isEmpty()")
            return
        }
        post {
            removeAllViews()
            val paint = Paint()
            val tv = itemViews.maxBy { it.text.length }//以最长的字符串为基准
            paint.textSize = tv!!.textSize
            var maxStringWidth = paint.measureText(tv.text.toString()).toInt()
            val fontMetrics = paint.fontMetrics
            var stringHeight = (fontMetrics.descent - fontMetrics.ascent).toInt()
            val textWidth = width / column * 0.9f
            val scale = textWidth / maxStringWidth
            Log.d(TAG, "scale:$scale")
            maxStringWidth = textWidth.toInt()
            val w = width / 4 * 0.05f
            val w1 = width / column * 0.05f
            val spaceWidth = if (w > w1) {//间隔
                w1.toInt()
            } else {
                w.toInt()
            }
            stringHeight = (stringHeight * scale).toInt()
            itemViews.forEach {
                it.setTextSize(TypedValue.COMPLEX_UNIT_PX, stringHeight * 0.5f)
                it.gravity = Gravity.CENTER
                it.includeFontPadding = true
            }
            Log.d(TAG, "w:$maxStringWidth,pw:$spaceWidth,h:$stringHeight")

            //更改layout
            var lines = itemViews.size / column
            if (itemViews.size % column != 0) lines++
            layoutParams.height = (stringHeight + spaceWidth) * lines

            //添加到viewGroup
            for (i in 0 until itemViews.size) {
                val textView = itemViews[i]
                if (clickListener != null) {
                    textView.setOnClickListener {
                        clickListener?.onClick(indexOfChild(textView), textView)
                    }
                }
                val x = (width / column) * (i % column)
                val y = (stringHeight) * (i / column)
                textView.layout(x, y + spaceWidth, x + maxStringWidth, y + stringHeight - spaceWidth)
                Log.d(TAG, "l:$x,t:${y + spaceWidth},r:${x + maxStringWidth},b:${y + stringHeight - spaceWidth}")
                addView(textView)
            }
        }
    }

    private var clickListener: ItemClickListener? = null

    public interface ItemClickListener {
        fun onClick(index: Int, view: View)
    }

    public fun setItemClickListener(clickListener: ItemClickListener) {
        this.clickListener = clickListener
    }
}
