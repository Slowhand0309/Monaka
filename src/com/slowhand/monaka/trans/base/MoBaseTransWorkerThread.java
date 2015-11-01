package com.slowhand.monaka.trans.base;

import com.slowhand.monaka.thread.MoBaseWorkerThread;
import com.slowhand.monaka.trans.MoTransException;
import com.slowhand.monaka.trans.MoTransManager;
import com.slowhand.monaka.trans.MoTransStatus;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

/**
 * 通信処理用スレッド基底クラス
 * 
 * @see MoBaseWorkerThread
 * @author slowhand0309
 */
public abstract class MoBaseTransWorkerThread extends MoBaseWorkerThread {

    private static final String TAG = MoBaseTransWorkerThread.class.getSimpleName();

    private SparseArray<MoBaseTransAnalyzer> mAnalyzerArray;

    /**
     * コンストラクタ
     * 
     * @param name スレッド名
     */
    public MoBaseTransWorkerThread(String name) {
        super(name);

        mAnalyzerArray = new SparseArray<MoBaseTransAnalyzer>();
    }

    /**
     * Handler作成
     */
    @Override
    public Handler createHandler() {
        Handler handler = new Handler() {

            /**
             * メッセージ受信時
             */
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case MoTransManager.TRANS_MESSAGE_LISTEN:
                        /* Listen */
                        onListen(msg.arg1, msg.obj);
                        break;

                    case MoTransManager.TRANS_MESSAGE_CONNECTING:
                        /* 接続中 */
                        onConnecting(msg.arg1, msg.obj);
                        break;

                    case MoTransManager.TRANS_MESSAGE_CONNECTED:
                        /* 接続後 */
                        onConnected(msg.arg1, msg.obj);
                        break;

                    case MoTransManager.TRANS_MESSAGE_RECEIVE:
                        /* 受信時 */
                        Object obj = onAnalyze(msg.arg1, msg.obj, msg.arg2);
                        onReceive(msg.arg1, obj, msg.arg2);
                        break;

                    case MoTransManager.TRANS_MESSAGE_STATECHANGE:
                        /* 状態変更 */
                        Object[] params = (Object[]) msg.obj;
                        MoTransStatus status = (MoTransStatus) params[0];
                        String message = (String) params[1];
                        onStateChanged(msg.arg1, status, message);
                        break;

                    case MoTransManager.TRANS_MESSAGE_LOST:
                        /* 接続LOST */
                        onLost(msg.arg1);
                        break;

                    case MoTransManager.TRANS_MESSAGE_FAILED:
                        /* 接続失敗 */
                        onFailed(msg.arg1);
                        break;

                    case MoTransManager.TRANS_MESSAGE_DISCONNECT:
                        /* 切断 */
                        onDisconnect(msg.arg1);
                        break;

                    default:
                        break;
                }
            }
        };

        return handler;
    }

    /**
     * 解析クラス登録
     * 
     * @param analyzer {@link MoBaseTransAnalyzer}
     * @throws MoTransException
     */
    public void registAnalyzer(MoBaseTransAnalyzer analyzer)
            throws MoTransException {

        int id = analyzer.getProcId();
        if (mAnalyzerArray.get(id) != null) {
            /* 既に登録済み */
            throw new MoTransException("packet analyzer already regist " + id);
        }

        Log.i(TAG, "regist packet anlyzer. " + id);
        mAnalyzerArray.put(id, analyzer);
    }

    /**
     * Listen時
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     */
    protected void onListen(int id, Object obj) {
        /* 継承先で実装 */
    }

    /**
     * 接続中
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     */
    protected void onConnecting(int id, Object obj) {
        /* 継承先で実装 */
    }

    /**
     * 接続後
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     */
    protected void onConnected(int id, Object obj) {
        /* 継承先で実装 */
    }

    /**
     * 解析処理時
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     * @param size データサイズ
     * @return 解析データ
     */
    protected Object onAnalyze(int id, Object obj, int size) {
        /* データ解析クラス取得 */
        MoBaseTransAnalyzer p = mAnalyzerArray.get(id);
        if (p != null) {
            return p.analyze(obj, size);
        }
        return obj;
    }

    /**
     * 受信時
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     * @param size データサイズ
     */
    protected void onReceive(int id, Object obj, int size) {
        /* 継承先で実装 */
    }

    /**
     * 状態変更
     * 
     * @param id 通信処理ID
     * @param status 状態
     * @param message メッセージ
     */
    protected void onStateChanged(int id, MoTransStatus status, String message) {
        /* 継承先で実装 */
    }

    /**
     * Lost時
     * 
     * @param id 通信処理ID
     */
    protected void onLost(int id) {

    }

    /**
     * 接続失敗
     * 
     * @param id 通信処理ID
     */
    protected void onFailed(int id) {

    }

    /**
     * 切断時
     * 
     * @param id 通信処理ID
     */
    protected void onDisconnect(int id) {
        /* 継承先で実装 */
    }
}
