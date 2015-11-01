package com.slowhand.monaka.db;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import com.slowhand.monaka.loader.MoBaseAsyncTaskLoader;

/**
 * SQLiteローダークラス<br>
 * バックグラウンドでgetListを行う
 *
 * @see RSLibAsyncTaskLoader
 * @author slowhand0309
 */
public class MoSqlLoader extends
        MoBaseAsyncTaskLoader<List<? extends MoBaseParam>> {

    private static final String TAG = MoSqlLoader.class.getSimpleName();

    public static final String ARGKEY_DAOID = "dao_id";
    public static final int LOADER_ID = 0x00000002;

    private int mDaoId;
    private String[] mSelection;
    private String[] mArgs;
    private String mOrderBy;

    /**
     * コンストラクタ
     *
     * @param id Loader ID
     * @param daoId Database Access オブジェクトID
     * @param context {@link Context}
     */
    public MoSqlLoader(int id, int daoId, Context context) {
        super(id, context);

        mDaoId = daoId;
    }

    /**
     * Where句以降を設定
     *
     * @param selection 条件対象カラム名
     * @param args 条件値
     */
    public void setSelectionArgs(String[] selection, String[] args) {
        mSelection = selection;
        mArgs = args;
    }

    /**
     * OrderBy句の設定
     *
     * @param orderBy OrderBy句
     */
    public void setOrderBy(String orderBy) {
        mOrderBy = orderBy;
    }

    /**
     * ローダー生成<br>
     * 特に外部Loaderの使用等がなければ自インスタンスを返す
     *
     * @param id 固有ID
     * @param args {@link Bundle}
     */
    @Override
    protected Loader<List<? extends MoBaseParam>> onCreate(int id,
            Bundle args) {
        return this;
    }

    /**
     * バックグラウンド処理
     */
    @Override
    public List<? extends MoBaseParam> loadInBackground() {

        List<? extends MoBaseParam> result = null;
        synchronized (this) {
            /* Database Access オブジェクトの取得 */
            MoSqliteHelper helper = MoSqliteHelper.getInstance(getContext());
            MoBaseDao dao = helper.getDao(mDaoId);
            if (dao == null) {
                Log.e(TAG, "database access object null. id => " + mDaoId);
                return result;
            }

            try {
                result = dao.getList(mSelection, mArgs, mOrderBy);
            } catch (MoSqliteException e) {
                Log.e(TAG, "RSLibSqliteException", e);
            }
        }
        return result;
    }
}
