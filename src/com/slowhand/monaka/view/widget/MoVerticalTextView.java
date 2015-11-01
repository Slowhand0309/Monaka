package com.slowhand.monaka.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 縦後方TextView
 * 
 * @author slowhand0309
 */
public class MoVerticalTextView extends TextView {

    private static final int DEFAULT_TEXT_SIZE = 30;

    private int mTextSize = DEFAULT_TEXT_SIZE;
    private Paint mPaint = new Paint();

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     */
    public MoVerticalTextView(Context context) {
        super(context, null);
    }

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public MoVerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     * @param defStyle デフォルトスタイル
     */
    public MoVerticalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * テキストサイズの設定
     * 
     * @param size テキストサイズ
     */
    public void setTextSize(int size) {
        mTextSize = size;
    }

    /**
     * 描画
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        String text = super.getText().toString();

        int width = canvas.getWidth();

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        float measureSize = mPaint.measureText(text);

        int low = (int) (measureSize / text.length());
        int h = low;
        int w = (int) ((width - low) / 2);
        if (w < 0)
            w = 0;

        for (int i = 0; i < text.length(); i++) {
            String charAt = String.valueOf(text.charAt(i));
            canvas.drawText(charAt, w, h, mPaint);
            h = h + low;
        }
    }
}
