package com.slowhand.monaka.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.List;

/**
 * Database Accessクラス
 *
 * @author slowhand0309
 */
public abstract class MoBaseDao {

    private static final String TAG = MoBaseDao.class.getSimpleName();

    private static final String EQUAL_QUESTION = "=?";
    private static final String AND_EX = " AND ";

    private int mId;
    private MoSqliteHelper mSqliteHelper;

    /**
     * テーブル名取得
     *
     * @return テーブル名
     */
    public abstract String getTableName();

    /**
     * クエリーされた{@link Cursor}から{@link MoBaseParam}へ変換
     *
     * @param cursor {@link Cursor}
     * @return {@link MoBaseParam}
     */
    protected abstract MoBaseParam toParam(Cursor cursor);

    /**
     * {@link MoBaseParam}から{@link ContentValues}へ変換
     *
     * @param param {@link MoBaseParam}
     * @return {@link ContentValues}
     */
    protected abstract ContentValues toContentValues(MoBaseParam param);

    /**
     * パラメータリスト作成
     *
     * @return パラメータリスト
     */
    protected abstract List<? extends MoBaseParam> createList();

    /**
     * コンストラクタ
     *
     * @param id 固有ID
     */
    public MoBaseDao(int id, Context context) {
        mId = id;
        mSqliteHelper = MoSqliteHelper.getInstance(context);
    }

    /**
     * 固有IDの取得
     *
     * @return 固有ID
     */
    public int getId() {
        return mId;
    }

    /**
     * パラメータを一件分読み込む
     *
     * @param selection Where句キー配列
     * @param args Where句キー値配列
     * @return {@link MoBaseParam}
     * @throws MoSqliteException
     */
    public MoBaseParam load(String[] selection, String[] args)
            throws MoSqliteException {

        MoBaseParam param = null;
        SQLiteDatabase db = mSqliteHelper.getReadableDatabase();
        try {

            Cursor cursor = db.query(getTableName(), null,
                    toSelection(selection), args, null, null, null);

            cursor.moveToFirst();
            if (cursor.getCount() != 1) {
                /* 複数クエリーされた場合は例外を投げる */
                throw new MoSqliteException("queried in duplicate!!");
            }

            param = toParam(cursor);
            cursor.close();
        } catch (SQLiteException e) {
            throw new MoSqliteException(e.getMessage());
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return param;
    }

    /**
     * パラメータリスト取得
     *
     * @param selection Where句キー配列
     * @param args Where句キー値配列
     * @param orderBy オーダーバイ句
     * @return パラメータリスト
     * @throws MoSqliteException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<? extends MoBaseParam> getList(String[] selection,
            String[] args, String orderBy) throws MoSqliteException {

        List list = null;
        SQLiteDatabase db = mSqliteHelper.getReadableDatabase();
        try {

            Cursor cursor = db.query(getTableName(), null,
                    toSelection(selection), args, null, null, orderBy);

            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                throw new MoSqliteException("getList not query.");
            }

            /* クエリー後ArrayListへ追加する */
            list = createList();
            while (!cursor.isAfterLast()) {
                list.add(toParam(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            throw new MoSqliteException(e.getMessage());
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return list;
    }

    /**
     * 登録処理
     *
     * @param param 登録パラメータ
     * @throws MoSqliteException
     */
    public void insert(MoBaseParam param) throws MoSqliteException {

        SQLiteDatabase db = mSqliteHelper.getWritableDatabase();
        ContentValues values = toContentValues(param);
        if (values == null) {
            throw new MoSqliteException("ContextValues convert failed.");
        }

        try {

            /* 登録処理実行 */
            db.beginTransaction();
            db.insert(getTableName(), null, values);
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            throw new MoSqliteException(e.getMessage());
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * 削除処理
     *
     * @param selection Where句キー配列
     * @param args Where句キー値配列
     * @throws MoSqliteException
     */
    public void delete(String[] selection, String[] args)
            throws MoSqliteException {

        SQLiteDatabase db = mSqliteHelper.getWritableDatabase();
        try {

            db.beginTransaction();
            db.delete(getTableName(), toSelection(selection), args);
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            throw new MoSqliteException(e.getMessage());
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * String型の配列からSelection文字列を作成
     *
     * @param selection Where句キー配列
     * @return Selection文字列
     */
    protected String toSelection(String[] selection) {

        if (selection == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selection.length; i++) {
            sb.append(selection[i]);
            sb.append(EQUAL_QUESTION);
            if (i != (selection.length - 1)) {
                sb.append(AND_EX);
            }
        }
        return sb.toString();
    }
}
