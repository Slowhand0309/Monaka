package com.slowhand.monaka.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import com.slowhand.monaka.log.MoJournalLogManager;
import com.slowhand.monaka.log.MoSqlJournalLog;
import com.slowhand.monaka.util.MoContextUtil;
import com.slowhand.monaka.util.MoStringUtil;
import com.slowhand.monaka.util.MoSysUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

/**
 * SQLiteヘルパークラス
 *
 * @see SQLiteOpenHelper
 * @author slowhand0309
 */
public class MoSqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = MoSqliteHelper.class.getSimpleName();

    private static final String CHARSET_NAME = "UTF-8";
    private static final String SPLIT_CHAR = "&";
    private static final String CREATE_SQL_PATH = "sql/create";
    private static final String UPGRADE_SQL_PATH = "sql/upgrade/ver_";
    private static final String DATABASE_NAME_UNKOWN = "android_unkown_app.db";
    private static final String DATABASE_PREFIX = ".db";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private MoSqlJournalLog mSqlJournalLog;
    private static MoSqliteHelper mInstance;

    private SparseArray<MoBaseDao> mDaoArray;

    /**
     * インスタンスの取得
     *
     * @param context {@link Context}
     * @return {@link MoSqliteHelper}
     */
    public static synchronized MoSqliteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MoSqliteHelper(context);
        }
        return mInstance;
    }

    /**
     * 終了処理
     */
    public static void onDestory() {
        mInstance = null;
    }

    /**
     * Database名の取得
     *
     * @param context {@link Context}
     * @return Database名
     */
    public static String getDatabaseName(Context context) {
        String appName = MoContextUtil.getApplicationName(context);
        if (MoStringUtil.isEmpty(appName)) {
            return DATABASE_NAME_UNKOWN;
        }
        return appName.toLowerCase(Locale.getDefault()) + DATABASE_PREFIX;
    }

    /**
     * コンストラクタ
     *
     * @param context {@link Context}
     */
    private MoSqliteHelper(Context context) {
        super(context, getDatabaseName(context), null, DATABASE_VERSION);

        mContext = context;
        mSqlJournalLog = MoJournalLogManager.getInstance(context).getSqlJournalLog();
        mDaoArray = new SparseArray<MoBaseDao>();
    }

    /**
     * Database Accessクラスの登録
     *
     * @param dao {@link MoBaseDao}
     * @throws MoSqliteException
     */
    public void registDao(MoBaseDao dao) throws MoSqliteException {

        if (dao == null) {
            throw new MoSqliteException("dao is null.");
        }
        if (mDaoArray.get(dao.getId()) != null) {
            throw new MoSqliteException("dao already regist. id : "
                    + dao.getId());
        }

        mDaoArray.put(dao.getId(), dao);
    }

    /**
     * Database Accessクラスの取得
     *
     * @param id 固有ID
     * @return {@link MoBaseDao}
     */
    public MoBaseDao getDao(int id) {
        return mDaoArray.get(id, null);
    }

    /**
     * DB作成時
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");

        try {

            /* Assetフォルダ内のSQL実行 */
            db.beginTransaction();
            execSql(db, CREATE_SQL_PATH);
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            Log.e(TAG, "onCreate SQLException.", e);
        } catch (IOException e) {
            Log.e(TAG, "onCreate IOException.", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * DB更新時
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade oldVersion :" + oldVersion + " newVersion :"
                + newVersion);

        if (oldVersion >= newVersion) {
            return;
        }

        int index = oldVersion;
        try {

            db.beginTransaction();

            /* 最新バージョンまでマイグレート */
            while (index != newVersion) {
                index++;

                /* 対象バージョンのupgrade実行 */
                String dirName = UPGRADE_SQL_PATH + String.valueOf(index);
                execSql(db, dirName);
            }
        } catch (SQLException e) {
            Log.e(TAG, "onUpgrade SQLException.", e);
        } catch (IOException e) {
            Log.e(TAG, "onUpgrade IOException.", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Assetフォルダ内のSQLを実行
     *
     * @param db {@link SQLiteDatabase}
     * @param assetDir Asset内フォルダパス
     * @throws IOException
     */
    private void execSql(SQLiteDatabase db, String assetDir) throws IOException {

        AssetManager as = mContext.getResources().getAssets();
        String files[] = as.list(assetDir);
        for (String file : files) {
            String str = readFile(as.open(assetDir + File.separator + file));
            mSqlJournalLog.outputLog(TAG, str);
            for (String sql : str.split(SPLIT_CHAR)) {
                db.execSQL(sql);
            }
        }
    }

    /**
     * ファイル読込
     *
     * @param is {@link InputStream}
     * @return 文字列
     * @throws IOException
     */
    private String readFile(InputStream is) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                CHARSET_NAME));

        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
            sb.append(MoSysUtil.getLineSeparator());
        }
        return sb.toString();
    }
}
