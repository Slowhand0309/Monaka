package com.slowhand.monaka.dir;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import com.slowhand.monaka.util.MoContextUtil;
import com.slowhand.monaka.util.MoStringUtil;

/**
 * アプリケーション用ディレクトリ管理クラス
 *
 * <pre>
 * app_root
 *    - log
 *       ログ出力ディレクトリ
 *       ex) application.log, exception.log...
 *    - export
 *       エクスポートファイルディレクトリ
 *       ex) database.csv...
 *    - import
 *       インポートファイルディレクトリ
 *       ex) personal.csv...
 * </pre>
 *
 * @author slowhand309
 */
public class MoAppDirManager {

    private static final String TAG = MoAppDirManager.class.getSimpleName();

    /* アプリケーションルートディレクトリ */
    private static final String ROOT_DIR = "appdir_root";

    /* ログディレクトリ */
    private static final String LOG_DIR = "log";

    /* エクスポートディレクトリ */
    private static final String EXPORT_DIR = "export";

    /* インポートディレクトリ */
    private static final String IMPORT_DIR = "import";

    private static final String APPLICATION_NAME_UNKNOWN = "unknown";

    private Context mContext;
    private String mApplicationName;
    private StringBuilder mBuilder = new StringBuilder();

    private static MoAppDirManager mInstance;

    /**
     * インスタンス取得
     *
     * @param context {@link Context}
     * @return インスタンス
     */
    public static synchronized MoAppDirManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MoAppDirManager(context);
        }
        return mInstance;
    }

    /**
     * コンストラクタ
     *
     * @param context {@link Context}
     */
    private MoAppDirManager(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * アプリケーション名の設定<br>
     * 未設定の場合はapplication:lableの値を使用
     *
     * @param name アプリケ−ション名
     */
    public void setApplicationName(String name) {
        mApplicationName = name;
    }

    /**
     * アプリケーションルートディレクトリ取得
     *
     * @return ディレクトリパス
     */
    public String getAppRootDir() {

        String appName = getApplicationName();

        mBuilder.setLength(0);
        mBuilder.append(Environment.getExternalStorageDirectory().getPath());
        mBuilder.append(File.separator);
        mBuilder.append(ROOT_DIR);
        mBuilder.append(File.separator);
        mBuilder.append(appName);
        mBuilder.append(File.separator);
        return mBuilder.toString();
    }

    /**
     * ログディレクトリ取得
     *
     * @return ログディレクトリ
     */
    public String getLogDir() {

        String path = getAppRootDir() + LOG_DIR + File.separator;
        if (!mkdir(path)) {
            Log.e(TAG, "mkdir failed. " + path);
        }
        return path;
    }

    /**
     * エクスポートディレクトリ取得
     *
     * @return エクスポートディレクトリ
     */
    public String getExportDir() {

        String path = getAppRootDir() + EXPORT_DIR + File.separator;
        if (!mkdir(path)) {
            Log.e(TAG, "mkdir failed. " + path);
        }
        return path;
    }

    /**
     * インポートディレクトリ取得
     *
     * @return インポートディレクトリ
     */
    public String getImportDir() {

        String path = getAppRootDir() + IMPORT_DIR + File.separator;
        if (!mkdir(path)) {
            Log.e(TAG, "mkdir failed. " + path);
        }
        return path;
    }

    /**
     * アプリケーション名取得
     *
     * @return アプリケーション名
     */
    public String getApplicationName() {

        if (MoStringUtil.isEmpty(mApplicationName)) {
            mApplicationName = MoContextUtil.getApplicationName(mContext);
            if (MoStringUtil.isEmpty(mApplicationName)) {
                mApplicationName = APPLICATION_NAME_UNKNOWN;
            }
        }
        return mApplicationName;
    }

    /**
     * ディレクトリ作成
     *
     * @param path パス
     * @return true : 成功 false : 失敗
     */
    private boolean mkdir(String path) {
        if (MoStringUtil.isEmpty(path)) {
            return false;
        }

        final File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }
}
