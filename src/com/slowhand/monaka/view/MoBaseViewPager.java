package com.slowhand.monaka.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager基底クラス
 * 
 * @author slowhand0309
 */
public class MoBaseViewPager extends ViewPager {

    private boolean mEnabled = false;

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     */
    public MoBaseViewPager(Context context) {
        this(context, null);
    }

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public MoBaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * ViewPageerのタッチイベントを実装
     * 
     * @param event {@link MotionEvent}
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN || mEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * ViewPageerのタッチイベントを実装
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (mEnabled) {
            return super.onTouchEvent(arg0);
        }
        return false;
    }

    /**
     * OnTouch時のページング処理可否設定
     * 
     * @see ViewPager#onInterceptTouchEvent(MotionEvent)
     * @see ViewPager#onTouchEvent(MotionEvent)
     * @param enabled 可否
     */
    public void setPagingEnabled(boolean enabled) {
        mEnabled = enabled;
    }
}
