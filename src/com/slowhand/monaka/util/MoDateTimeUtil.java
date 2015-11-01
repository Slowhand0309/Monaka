package com.slowhand.monaka.util;

import android.text.format.DateFormat;

/**
 * 日時処理ユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoDateTimeUtil {

    private static final String CURRENT_DATETIME_FORMAT = "yyyy/MM/dd kk:mm:ss";

    /**
     * 現在日時の文字列取得
     * yyyy/MM/dd kk:mm:ss
     * 
     * @return 文字列
     */
    public static String getCurrentDateTimeStr() {
        return (String) DateFormat.format(CURRENT_DATETIME_FORMAT,
                System.currentTimeMillis());
    }
}
