/**
 *
 */
package com.slowhand.monaka.pref;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import java.util.Locale;

import com.slowhand.monaka.util.MoContextUtil;
import com.slowhand.monaka.util.MoStringUtil;

/**
 * プリファレンス管理クラス
 *
 * @author slowhand0309
 */
public final class MoPreferenceManager {

    private static final String TAG = MoPreferenceManager.class.getSimpleName();

    /** プリファレンス名プレフィックス */
    private static final String PREF_PREFIX = "_pref";

    /**
     * プリファレンス名取得
     *
     * @param context {@link Context}
     * @return プリファレンス名
     */
    public static String getPreferenceName(Context context) {
        return MoPreferenceManager.getPreferenceName(context, null);
    }

    /**
     * プリファレンス名取得<br>
     * パッケージ指定
     *
     * @param context {@link Context}
     * @param packageName パッケージ名
     * @return プリファレンス名
     */
    public static String getPreferenceName(Context context, String packageName) {
        if (context == null) {
            return MoStringUtil.EMPTY;
        }

        if (MoStringUtil.isNotEmpty(packageName)) {
            try {
                context = MoContextUtil
                        .getPackageContext(context, packageName);
            } catch (NameNotFoundException e) {
                Log.e(TAG, "Package Not Found " + packageName, e);
                return MoStringUtil.EMPTY;
            }
        }

        String appName = MoContextUtil.getApplicationName(context);
        if (MoStringUtil.isEmpty(appName)) {
            return MoStringUtil.EMPTY;
        }

        return appName.toLowerCase(Locale.getDefault()) + PREF_PREFIX;
    }
}
