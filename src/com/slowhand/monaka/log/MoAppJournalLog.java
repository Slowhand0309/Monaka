package com.slowhand.monaka.log;

import com.slowhand.monaka.util.MoDateTimeUtil;
import com.slowhand.monaka.util.MoStringUtil;

import android.content.Context;
import android.util.Log;

/**
 * アプリケーションジャーナルログクラス
 *
 * @author slowhand0309
 */
public class MoAppJournalLog extends MoBaseJournalLog {

    public static final String LOG_NAME = "application";

    /**
     * コンストラクタ
     *
     * @param context {@link Context}
     */
    public MoAppJournalLog(Context context) {
        super(context, LOG_NAME);
    }

    /**
     * ログ出力
     *
     * @oaram __LEVEL__ 出力レベル
     * @param obj パラメータ
     */
    @Override
    protected String writeLog(String __LEVEL__, Object obj) {
        if (!(obj instanceof String)) {
            return MoStringUtil.EMPTY;
        }

        String tag = new Throwable().getStackTrace()[1].getClassName();

        if (__LEVEL__.equals(LEVEL_INFO)) {
            Log.i(tag, (String) obj);
        } else if (__LEVEL__.equals(LEVEL_WARN)) {
            Log.w(tag, (String) obj);
        } else if (__LEVEL__.equals(LEVEL_ERROR)) {
            Log.e(tag, (String) obj);
        } else if (__LEVEL__.equals(LEVEL_DEBUG)) {
            Log.d(tag, (String) obj);
        } else if (__LEVEL__.equals(LEVEL_VERBOSE)) {
            Log.v(tag, (String) obj);
        }

        mBuilder.setLength(0);
        mBuilder.append(MoDateTimeUtil.getCurrentDateTimeStr());
        mBuilder.append(SPACE);
        mBuilder.append(tag);
        mBuilder.append(SPACE);
        mBuilder.append(__LEVEL__);
        mBuilder.append(SPACE);
        mBuilder.append(obj);
        mBuilder.append(LINE_SEPARATOR);
        return mBuilder.toString();
    }

}
