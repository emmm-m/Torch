package com.bucai.torch.view.main.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zia on 2018/5/26.
 */
public class GalleryPager extends ViewPager {

    private Thread thread;
    private GalleryPagerAdapter adapter;
    private int currentShowingPosition = 0;
    private Paint paint;
    private int space = 0;//圆圈间距
    private int radius = 0;//圆圈半径
    private PagerCircle pagerCircle;
    private static final String TAG = "GalleryPager";

    public GalleryPager(@NonNull Context context) {
        super(context);
        init();
    }

    public GalleryPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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

    public void setViews(ArrayList<View> views, final PagerCircle pagerCircle) {
        adapter = new GalleryPagerAdapter();
        adapter.setList(views);
        setAdapter(adapter);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (adapter.getCount() == 0) {
                        Log.e(TAG, "views count == 0");
                        break;
                    }
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentItem(currentShowingPosition);
                            pagerCircle.setCurrentPosition(currentShowingPosition, adapter.getCount());
                            invalidate();//mdzz，这居然不行
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    currentShowingPosition = (currentShowingPosition + 1) % (adapter.getCount());
                }
                Thread.yield();
            }
        });
        thread.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        thread.interrupt();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int curY = getHeight() / 5 * 4;
        int circleSize = adapter.getCount();
        int totalWidth = circleSize * radius * 2 + space * (circleSize - 1);
        int curX = (getWidth() - totalWidth) / 2;
        for (int i = 0; i < circleSize; i++) {
            canvas.drawCircle(curX + radius, curY, radius, paint);
            curX = curX + radius * 2 + space;
            Log.e(TAG, "curX:" + curX);
        }


    }

    private int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
