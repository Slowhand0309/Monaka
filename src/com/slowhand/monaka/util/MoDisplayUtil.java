/**
 *
 */

package com.slowhand.monaka.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * ディスプレイユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoDisplayUtil {

    /**
     * {@link DisplayMetrics}取得
     * 
     * @param activity {@link Activity}
     * @return {@link DisplayMetrics}
     */
    public static final DisplayMetrics getDisplayMetrics(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 画面スケールの取得
     * 
     * @param activity {@link Activity}
     * @return スケール値
     */
    public static float getScaledDensity(Activity activity) {
        DisplayMetrics metrics = MoDisplayUtil.getDisplayMetrics(activity);
        return metrics.scaledDensity;
    }
}
