package com.slowhand.monaka.log;

import java.lang.Thread.UncaughtExceptionHandler;

import com.slowhand.monaka.util.MoDateTimeUtil;
import com.slowhand.monaka.util.MoStringUtil;

import android.content.Context;

/**
 * 例外時ジャーナルログクラス
 *
 * @author slowhand0309
 */
public class MoUncaughtExJournalLog extends MoBaseJournalLog implements
        UncaughtExceptionHandler {

    public static final String LOG_NAME = "uncaught_ex";

    private boolean mCrashing = false;
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * コンストラクタ
     *
     * @param context {@link Context}
     */
    public MoUncaughtExJournalLog(Context context) {
        super(context, LOG_NAME);
    }

    /**
     * ハンドラ設定
     *
     * @param h {@link UncaughtExceptionHandler}
     */
    public void setDefaultUncaughtExHandler(UncaughtExceptionHandler h) {
        mDefaultHandler = h;
    }

    /**
     * 例外キャッチ
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!mCrashing) {
            mCrashing = true;

            writeHeader();
            outputLog(LEVEL_ASSERT, ex);
        }

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * ログ出力
     *
     * @param __LEVEL__ 出力レベル
     * @param obj {@link Throwable}
     */
    @Override
    protected String writeLog(String __LEVEL__, Object obj) {
        if (!(obj instanceof Throwable)) {
            return MoStringUtil.EMPTY;
        }

        Throwable th = (Throwable) obj;
        String className = th.getStackTrace()[1].getClassName();

        mBuilder.setLength(0);
        mBuilder.append(MoDateTimeUtil.getCurrentDateTimeStr());
        mBuilder.append(SPACE);
        mBuilder.append(className);
        mBuilder.append(SPACE);
        mBuilder.append(th.getLocalizedMessage());
        mBuilder.append(LINE_SEPARATOR);

        return mBuilder.toString();
    }
}
