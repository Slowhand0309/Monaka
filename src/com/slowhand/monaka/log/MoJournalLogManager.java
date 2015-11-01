package com.slowhand.monaka.log;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * ジャーナルログ管理クラス
 *
 * @author slowhand0309
 */
public final class MoJournalLogManager {

    /** アプリケーションジャーナルログ名 */
    private static final String APP_JOURNAL_LOG = MoAppJournalLog.LOG_NAME;

    /** 例外ジャーナルログ名 */
    private static final String EX_JOURNAL_LOG = MoUncaughtExJournalLog.LOG_NAME;

    /** SQLジャーナルログ名 */
    private static final String SQL_JOURNAL_LOG = MoSqlJournalLog.LOG_NAME;

    /** ジャーナルログマップ */
    private Map<String, MoBaseJournalLog> mJournalLogMap = new HashMap<String, MoBaseJournalLog>();

    /** インスタンス   */
    private static MoJournalLogManager mInstance;


    /**
     * インスタンス取得
     *
     * @return インスタンス
     */
    public static synchronized MoJournalLogManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MoJournalLogManager(context);
        }
        return mInstance;
    }

    /**
     * コンストラクタ
     */
    private MoJournalLogManager(Context context) {
        initialize(context);
    }

    /**
     * 初期化
     *
     * @param context {@link Context}
     */
    public void initialize(Context context) {

        MoAppJournalLog appLog = new MoAppJournalLog(context);
        appLog.writeHeader();
        mJournalLogMap.put(APP_JOURNAL_LOG, appLog);

        MoUncaughtExJournalLog exLog = new MoUncaughtExJournalLog(context);
        mJournalLogMap.put(EX_JOURNAL_LOG, exLog);

        MoSqlJournalLog sqlLog = new MoSqlJournalLog(context);
        sqlLog.writeHeader();
        mJournalLogMap.put(SQL_JOURNAL_LOG, sqlLog);
    }

    /**
     * アプリケーションジャーナルログ取得
     *
     * @return {@link MoAppJournalLog}
     */
    public MoAppJournalLog getAppJournalLog() {
        return (MoAppJournalLog) getJournalLog(APP_JOURNAL_LOG);
    }

    /**
     * 例外ジャーナルログ取得
     *
     * @return {@link MoUncaughtExJournalLog}
     */
    public MoUncaughtExJournalLog getUncaughtExJournalLog() {
        return (MoUncaughtExJournalLog) getJournalLog(EX_JOURNAL_LOG);
    }

    /**
     * SQLジャーナルログ取得
     *
     * @return {@link MoSqlJournalLog}
     */
    public MoSqlJournalLog getSqlJournalLog() {
        return (MoSqlJournalLog) getJournalLog(SQL_JOURNAL_LOG);
    }

    /**
     * ジャーナルログ取得
     *
     * @param name ログ名
     * @return ジャーナルログインスタンス
     */
    private MoBaseJournalLog getJournalLog(String name) {
        if (mJournalLogMap.containsKey(name)) {
            return mJournalLogMap.get(name);
        }
        return null;
    }
}
