package com.slowhand.monaka.log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.slowhand.monaka.dir.MoAppDirManager;
import com.slowhand.monaka.util.MoDateTimeUtil;
import com.slowhand.monaka.util.MoStringUtil;
import com.slowhand.monaka.util.MoSysUtil;

import android.content.Context;
import android.util.Log;

/**
 * ジャーナルログ基底クラス
 *
 * @author slowhand0309
 */
public abstract class MoBaseJournalLog {

    private static final String TAG = "FdxBaseJournalLog";

    /* 出力レベル  */
    public static final String LEVEL_ASSERT = "assert";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_WARN = "warn";
    public static final String LEVEL_ERROR = "error";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_VERBOSE = "verbose";

    protected static final String EXTENSION = ".txt";

    protected static final String SEPARATE = "=============================================";
    protected static final String SEPARATE_SUB = "*******************************************";
    protected static final String LOG_NAME_FROM = "logging to ";
    protected static final String SPACE = "  ";
    protected static final String COLON = ":";
    protected static final String LINE_SEPARATOR = MoSysUtil
            .getLineSeparator();

    protected String mLogName;
    protected String mFilePath;
    protected StringBuilder mBuilder = new StringBuilder();

    /**
     * ログ出力
     *
     * @param __LEVEL__ 出力レベル
     * @param obj       パラメータ
     */
    protected abstract String writeLog(String __LEVEL__, Object obj);

    /**
     * コンストラクタ
     *
     * @param context {@link Context}
     * @param logName ログ名
     */
    public MoBaseJournalLog(Context context, String logName) {

        MoAppDirManager appDirMgr = MoAppDirManager.getInstance(context);
        mFilePath = appDirMgr.getLogDir() + logName + EXTENSION;
        mLogName = logName;
    }

    /**
     * ログ出力
     *
     * @param __LEVEL__ 出力レベル
     * @param obj パラメータ
     */
    public void outputLog(String __LEVEL__, Object obj) {
        String str = writeLog(__LEVEL__, obj);
        if (MoStringUtil.isNotEmpty(str)) {
            write(str);
        }
    }

    /**
     * ヘッダ出力
     *
     * @param TAG タグ
     * @param writer {@link BufferedWriter}
     * @throws IOException
     */
    public void writeHeader() {

        mBuilder.setLength(0);
        mBuilder.append(SEPARATE);
        mBuilder.append(LINE_SEPARATOR);
        mBuilder.append(SEPARATE_SUB);
        mBuilder.append(LINE_SEPARATOR);
        /* ��t �X�y�[�X ���O�� */
        mBuilder.append(MoDateTimeUtil.getCurrentDateTimeStr());
        mBuilder.append(SPACE);
        mBuilder.append(LOG_NAME_FROM);
        mBuilder.append(COLON);
        mBuilder.append(mLogName);
        mBuilder.append(LINE_SEPARATOR);
        mBuilder.append(SEPARATE_SUB);
        mBuilder.append(LINE_SEPARATOR);

        write(mBuilder.toString());
    }

    /**
     * フッター出力
     *
     * @param writer {@link BufferedWriter}
     * @throws IOException
     */
    public void writeFooter() {

        mBuilder.setLength(0);
        mBuilder.append(SEPARATE_SUB);
        mBuilder.append(LINE_SEPARATOR);

        write(mBuilder.toString());
    }

    /**
     * ファイル出力
     *
     * @param value 出力値
     */
    private void write(String value) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(mFilePath, true)));

            writer.write(value);
            writer.flush();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
