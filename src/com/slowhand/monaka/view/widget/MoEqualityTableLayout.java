package com.slowhand.monaka.view.widget;

import java.util.List;

import com.slowhand.monaka.util.MoMathUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * 矩形テーブルレイアウトビュー
 * 
 * @author slowhand0309
 */
public class MoEqualityTableLayout extends TableLayout {

    protected static final String TAG = MoEqualityTableLayout.class.getSimpleName();

    protected static final int PADDING = 5;

    protected Context mContext;
    protected List<View> mPostViews;

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     */
    public MoEqualityTableLayout(Context context) {
        this(context, null);
    }

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public MoEqualityTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     * レイアウト処理
     */
    public void layout() {

        int[] size = MoMathUtil.equalitySqure(mPostViews.size());
        int h = size[0];
        int w = size[1];
        Log.v(TAG, "onLayout post size h : " + h + " w : " + w);

        int index = 0;
        TableRow.LayoutParams clp = new TableRow.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        clp.weight = 1.0f;

        for (int i = 0; i < h; i++) {

            TableLayout.LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.weight = 1.0f;

            TableRow row = new TableRow(mContext);
            row.setPadding(PADDING, PADDING, PADDING, PADDING);
            row.setLayoutParams(lp);
            row.setGravity(Gravity.CENTER);
            this.addView(row);

            for (int j = 0; j < w; j++) {

                if (index >= mPostViews.size()) {
                    LinearLayout ll = new LinearLayout(mContext);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setGravity(Gravity.CENTER);
                    row.addView(ll, clp);
                    continue;
                }
                View v = mPostViews.get(index);
                if (v != null) {
                    row.addView(v, clp);
                    index++;
                }
            }
        }
    }

    /**
     * 配置Viewリスト設定
     * 
     * @param views Viewリスト
     */
    public void setViewList(List<View> views) {
        mPostViews = views;
    }

}
