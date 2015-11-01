package com.slowhand.monaka.log;

import com.slowhand.monaka.util.MoDateTimeUtil;
import com.slowhand.monaka.util.MoStringUtil;

import android.content.Context;


/**
 * SQLジャーナルログ
 *
 * @author slowhand0309
 */
public class MoSqlJournalLog extends MoBaseJournalLog {

    public static final String LOG_NAME = "sql";

    private static final String EXECUTE_SQL = "execute sql >";

    /**
     * コンストラクタ
     *
     * @param context
     */
    public MoSqlJournalLog(Context context) {
        super(context, LOG_NAME);
    }

    /**
     * ログ出力
     *
     * @oaram __TAG__ 出力レベル
     * @param obj パラメータ
     */
    @Override
    protected String writeLog(String __TAG__, Object obj) {
        if (!(obj instanceof String)) {
            return MoStringUtil.EMPTY;
        }

        mBuilder.setLength(0);
        mBuilder.append(MoDateTimeUtil.getCurrentDateTimeStr());
        mBuilder.append(SPACE);
        mBuilder.append(EXECUTE_SQL);
        mBuilder.append(LINE_SEPARATOR);
        mBuilder.append(obj.toString());
        mBuilder.append(LINE_SEPARATOR);
        return mBuilder.toString();
    }

}
