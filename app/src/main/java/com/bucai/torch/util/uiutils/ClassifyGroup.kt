package com.bucai.torch.util.uiutils

import android.content.Context
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
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
        val itemViews = strings.map {
            val textView = TextView(context)
            textView.text = it
            textView.textSize = textSize
            Log.e("row", "before:${textView.textSize}")
            textView.background = resources.getDrawable(id)
            textView.setTextColor(textColor)
            textView.gravity = Gravity.CENTER
            textView.includeFontPadding = false
//            textView.setBackgroundColor(Color.GREEN)
            textView
        }
        addItems(itemViews, column)
    }

    public fun addItems(itemViews: List<TextView>, column: Int) {
        if (column <= 0 || itemViews.isEmpty()) {
            Log.e(TAG, "column <= 0 || itemViews.isEmpty()")
            return
        }
        post {
            val paint = Paint()
            val tv = itemViews.maxBy { it.text.length }//以最长的字符串为基准
            paint.textSize = tv!!.textSize
            var w = paint.measureText(tv.text.toString()).toInt()
            var pw = (width / column - w) / 2
            val fontMetrics = paint.fontMetrics
            var h = (fontMetrics.descent - fontMetrics.ascent).toInt()

            if (pw <= 0) {
                val tw = width / 4 * 0.9f
                val scale = tw / w
                Log.e("zia", "scale:$scale")
                paint.textSize = tv.textSize * scale
                itemViews.forEach {
                    Log.e("emmm", "before:${it.textSize * scale}")
                    it.setTextSize(TypedValue.COMPLEX_UNIT_PX, h * 0.5f)
                    it.includeFontPadding = false
                    Log.e("emmm", "after:${it.textSize}")
                }
                w = tw.toInt()
                pw = (width / column - w) / 2
                h = (h * scale).toInt()
            }
            Log.e(TAG, "w:$w,pw:$pw,h:$h")

            //更改layout
            var lines = itemViews.size / column
            if (itemViews.size % column != 0) lines++
            layoutParams.height = (h + pw * 2) * lines + pw

            //添加到viewGroup
            for (i in 0 until itemViews.size) {
                val textView = itemViews[i]
                val x = (width / column) * (i % column) + pw / 2
                val y = (h + pw * 2) * (i / column)
                addView(textView)
                Log.e("zia", "l:$x,t:${y + pw},r:${x + w},b:${y + h + pw}")
                textView.layout(x, y + pw, x + w, y + h + pw)
            }
        }
    }
}
