package com.bucai.torch.view.main.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by zia on 2018/5/26.
 */
public class PagerCircle extends android.support.v7.widget.AppCompatTextView {

    private Paint paint;
    private int space = 0;//圆圈间距
    private int radius = 0;//圆圈半径
    private int position = 0;
    private int totalSize = 0;

    public PagerCircle(Context context) {
        super(context);
        init();
    }

    public PagerCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        space = dp2px(getContext(), 5);
        radius = dp2px(getContext(), 5);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setStrokeWidth(100);
    }

    public void setCurrentPosition(int currentPosition, int totalSize) {
        this.position = currentPosition;
        this.totalSize = totalSize;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int curY = getHeight() / 6 * 5;
        int totalWidth = totalSize * radius * 2 + space * (totalSize - 1);
        int curX = (getWidth() - totalWidth) / 2;
        for (int i = 0; i < totalSize; i++) {
            if (i == position) {
                paint.setAlpha(230);
            } else {
                paint.setAlpha(120);
            }
            canvas.drawCircle(curX + radius, curY, radius, paint);
            curX = curX + radius * 2 + space;
        }
    }

    private int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
