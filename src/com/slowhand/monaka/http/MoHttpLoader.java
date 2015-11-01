package com.slowhand.monaka.http;

import com.slowhand.monaka.loader.MoBaseAsyncTaskLoader;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

/**
 * HTTPローダークラス
 *
 * @see RSLibAsyncTaskLoader
 * @author slowhand0309
 */
public class MoHttpLoader extends MoBaseAsyncTaskLoader<MoHttpResponse> {

    private static final String TAG = MoHttpLoader.class.getSimpleName();

    /**
     * コンストラクタ
     *
     * @param id Loader ID
     * @param context {@link Context}
     */
    public MoHttpLoader(int id, Context context) {
        super(id, context);
    }

    /**
     * ローダー生成<br>
     * 特に外部Loaderの使用等がなければ自インスタンスを返す
     *
     * @param id 固有ID
     * @param args {@link Bundle}
     */
    @Override
    protected Loader<MoHttpResponse> onCreate(int id, Bundle args) {
        return this;
    }

    /**
     * バックグラウンド処理
     */
    @Override
    public MoHttpResponse loadInBackground() {
        Log.i(TAG, "loadInBackground id : " + getId());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return null;
    }

}
