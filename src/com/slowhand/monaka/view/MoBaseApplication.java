package com.slowhand.monaka.view;

import java.lang.Thread.UncaughtExceptionHandler;

import com.slowhand.monaka.dir.MoAppDirManager;
import com.slowhand.monaka.log.MoJournalLogManager;
import com.slowhand.monaka.log.MoUncaughtExJournalLog;
import com.slowhand.monaka.util.MoStringUtil;

import android.app.Application;

/**
 * アプリケーション基底クラス
 *
 * @author slowhand0309
 */
public class MoBaseApplication extends Application {

    /**
     * アプリケーション作成時
     */
    @Override
    public void onCreate() {
        super.onCreate();
        onSetUncaughtExceptionHandler();
    }

    /**
     * アプリケーション名作成<br />
     * デフォルト以外を使用する場合は継承先で実装
     *
     * @return アプリケーション名
     */
    protected String onCreateAppName() {
        return MoStringUtil.EMPTY;
    }

    /**
     * {@link UncaughtExceptionHandler}設定<br>
     */
    protected void onSetUncaughtExceptionHandler() {

        /* アプリケーション名取得 */
        String appName = onCreateAppName();
        MoAppDirManager adm = MoAppDirManager
                .getInstance(getApplicationContext());
        adm.setApplicationName(appName);

        MoUncaughtExJournalLog exLog = MoJournalLogManager.getInstance(this)
                .getUncaughtExJournalLog();

        if (exLog != null) {

            final UncaughtExceptionHandler h = Thread
                    .getDefaultUncaughtExceptionHandler();
            exLog.setDefaultUncaughtExHandler(h);

            Thread.setDefaultUncaughtExceptionHandler(exLog);
        }
    }
}
