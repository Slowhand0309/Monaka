package com.slowhand.monaka.loader;

import com.slowhand.monaka.loader.MoBaseAsyncTaskLoader.LoadHandler;

import android.app.LoaderManager;
import android.content.Loader;
import android.util.Log;
import android.util.SparseArray;

/**
 * {@link Loader}管理クラス
 * 
 * @author slowhand0309
 */
public class MoLoadManager {

    private static final String TAG = MoLoadManager.class.getSimpleName();

    private LoaderManager mLoaderManager;
    private SparseArray<MoBaseAsyncTaskLoader<?>> mLoaderArray;
    private MoLoaderCallbacks mCallbacks;

    /**
     * コンストラクタ
     * 
     * @param manager {@link LoaderManager}
     */
    public MoLoadManager(LoaderManager manager, MoLoaderCallbacks callback) {
        mLoaderManager = manager;
        mCallbacks = callback;
        mLoaderArray = new SparseArray<MoBaseAsyncTaskLoader<?>>();
    }

    /**
     * {@link MoBaseAsyncTaskLoader}登録
     * 
     * @param loader {@link MoBaseAsyncTaskLoader}
     * @return Loader固有ID
     */
    public int registAsyncTaskLoader(MoBaseAsyncTaskLoader<?> loader) {

        if (loader == null) {
            return -1;
        }

        int id = loader.getId();
        if (mLoaderArray.get(id) != null) {
            /* 既に登録有り */
            Log.w(TAG, "already regist loader id : " + id);
            return -1;
        }

        loader.setLoaderCallbacks(mCallbacks);
        mLoaderArray.put(id, loader);
        return id;
    }

    /**
     * ローダーの処理を開始する
     * 
     * @throws MoLoaderException
     */
    @SuppressWarnings("unchecked")
    public void startLoader() throws MoLoaderException {

        if (mLoaderManager == null) {
            throw new MoLoaderException("LoaderManager is null.");
        }

        if (mLoaderArray.size() == 0) {
            throw new MoLoaderException("Loader list is empty.");
        }

        /* リストに登録されているローダーを全て実行 */
        for (int i = 0; i < mLoaderArray.size(); i++) {

            MoBaseAsyncTaskLoader<?> l = mLoaderArray.valueAt(i);
            @SuppressWarnings("rawtypes")
            LoadHandler h = l.getLoadHandler();

            mLoaderManager.initLoader(l.getId(), null, h);
        }
    }

    /**
     * 全LoaderのLoading処理が完了したか判定
     * 
     * @return true : 全Loading処理完了 false : 未完了
     */
    public boolean isAllLoaderFinished() {

        for (int i = 0; i < mLoaderArray.size(); i++) {
            MoBaseAsyncTaskLoader<?> l = mLoaderArray.valueAt(i);
            if (!l.isLoaded()) {
                return false;
            }
        }
        return true;
    }
}
