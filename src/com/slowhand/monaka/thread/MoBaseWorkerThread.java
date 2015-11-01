package com.slowhand.monaka.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * 処理スレッド基底クラス
 * 
 * @author slowhand0309
 */
public abstract class MoBaseWorkerThread extends HandlerThread {

    private static final String TAG = MoBaseWorkerThread.class.getSimpleName();

    protected Handler mExternaHandler;
    protected Handler mWorkerHandler;

    /**
     * Handler作成
     * 
     * @return {@link Handler}
     */
    public abstract Handler createHandler();

    /**
     * 初期処理
     */
    public abstract void initialize() throws Exception;

    /**
     * 終了処理
     */
    public abstract void finalize();

    /**
     * コンストラクタ
     */
    public MoBaseWorkerThread(String name) {
        super(name);
    }

    /**
     * 実行時
     */
    @Override
    public synchronized void start() {
        super.start();

        Log.i(TAG, "begin thread " + getName() + " thread id "
                + Thread.currentThread().getId());

        mWorkerHandler = createHandler();
    }

    /**
     * 外部Handlerへの通知
     * 
     * @param what 種類
     * @param arg1 引数1
     * @param arg2 引数2
     * @param obj パラメータ
     */
    public void sendMessageToExternal(int what, int arg1, int arg2, Object obj) {
        if (mExternaHandler != null) {
            mExternaHandler.obtainMessage(what, arg1, arg2, obj).sendToTarget();
        }
    }

    /**
     * {@link Handler}登録
     * 
     * @param handler {@link Handler}
     */
    public void registHandler(Handler handler) {
        mExternaHandler = handler;
    }

    /**
     * Handler取得
     * 
     * @return {@link Handler}
     */
    public Handler getWorkerHandler() {
        return mWorkerHandler;
    }
}
