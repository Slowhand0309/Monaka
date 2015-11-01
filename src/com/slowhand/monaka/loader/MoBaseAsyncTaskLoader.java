package com.slowhand.monaka.loader;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

/**
 * 基底タスクローダークラス
 * 
 * @see AsyncTaskLoader
 * @author slowhand0309
 */
public abstract class MoBaseAsyncTaskLoader<D> extends AsyncTaskLoader<D> {

    private static final String TAG = MoBaseAsyncTaskLoader.class.getSimpleName();

    private int mId;
    private boolean mLoaded;
    private Bundle mArgs;
    private LoadHandler mLoadHandler;
    private MoLoaderCallbacks mCallbacks;

    /**
     * 継承先でインスタンスを生成し返す事
     * 
     * @param id 固有ID
     * @param args {@link Bundle}
     * @return インスタンス
     */
    protected abstract Loader<D> onCreate(int id, Bundle args);

    /**
     * コンストラクタ
     * 
     * @param id AsyncTaskLoaderの固有ID
     * @param context {@link Context}
     */
    public MoBaseAsyncTaskLoader(int id, Context context) {
        super(context);
        mId = id;
        mLoaded = false;
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
     * コールバックの登録
     * 
     * @param callbacks {@link MoLoaderCallbacks}
     */
    public void setLoaderCallbacks(MoLoaderCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    /**
     * {@link Bundle}の設定
     * 
     * @param args {@link Bundle}
     */
    public void setBundle(Bundle args) {
        mArgs = args;
    }

    /**
     * {@link Bundle}の取得
     * 
     * @return {@link Bundle}
     */
    public Bundle getBundle() {
        return mArgs;
    }

    /**
     * Loading開始時
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mLoaded = false;
    }

    /**
     * 現在のLoading状況の取得
     * 
     * @return Loading状況
     */
    public synchronized boolean isLoaded() {
        return mLoaded;
    }

    /**
     * {@link LoadHandler}の取得
     * 
     * @return {@link LoadHandler}
     */
    public LoadHandler getLoadHandler() {

        if (mLoadHandler == null) {
            mLoadHandler = new LoadHandler();
        }
        return mLoadHandler;
    }

    /**
     * コールバックハンドラクラス
     * 
     * @see LoaderManager.LoaderCallbacks
     * @author slowhand0309
     */
    public class LoadHandler implements LoaderManager.LoaderCallbacks<D> {

        @Override
        public Loader<D> onCreateLoader(int id, Bundle args) {
            Loader<D> d = onCreate(id, args);
            if (d != null) {
                d.forceLoad();
            }
            return d;
        }

        @Override
        public void onLoadFinished(Loader<D> loader, D data) {
            MoBaseAsyncTaskLoader<D> d = (MoBaseAsyncTaskLoader<D>) loader;
            mLoaded = true;
            if (mCallbacks != null) {
                /* コールバック経由で通知 */
                mCallbacks.onLoadFinished(mId, data, d.getBundle());
            }
        }

        @Override
        public void onLoaderReset(Loader<D> loader) {
            if (mCallbacks != null) {
                /* コールバック経由で通知 */
                mCallbacks.onReset(mId);
            }
        }
    }
}
