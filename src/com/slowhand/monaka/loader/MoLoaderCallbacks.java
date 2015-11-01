package com.slowhand.monaka.loader;

import android.os.Bundle;

/**
 * 複数Loaderからのコールバック
 * 
 * @author slowhand0309
 */
public interface MoLoaderCallbacks {

    /**
     * ローダー処理完了時
     * 
     * @param id 固有ID
     * @param data データ
     * @param args {@link Bundle}
     */
    public void onLoadFinished(int id, Object data, Bundle args);

    /**
     * ローダーリセット時
     * 
     * @param id 固有ID
     */
    public void onReset(int id);
}
