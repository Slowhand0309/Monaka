/**
 *
 */
package com.slowhand.monaka.trans;

import com.slowhand.monaka.trans.base.MoBaseTransFilter;
import com.slowhand.monaka.trans.base.MoBaseTransProc;

import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;

/**
 * 通信処理IF
 * 
 * @author slowhand0309
 */
public class MoTransManager implements MoTransNotify {

    private static final String TAG = MoTransManager.class.getSimpleName();

    public static final int TRANS_MESSAGE_LISTEN = 0x00000010;
    public static final int TRANS_MESSAGE_CONNECTING = 0x00000020;
    public static final int TRANS_MESSAGE_CONNECTED = 0x00000030;
    public static final int TRANS_MESSAGE_RECEIVE = 0x00000040;
    public static final int TRANS_MESSAGE_STATECHANGE = 0x00000050;
    public static final int TRANS_MESSAGE_DISCONNECT = 0x00000060;
    public static final int TRANS_MESSAGE_LOST = 0x00000070;
    public static final int TRANS_MESSAGE_FAILED = 0x00000080;

    private static MoTransManager mInstance;
    private Handler mHandler;
    private SparseArray<MoBaseTransProc> mProcArray;
    private SparseArray<MoBaseTransFilter> mFilterArray;

    /**
     * インスタンス取得
     * 
     * @return {@link MoTransManager}
     */
    public static synchronized MoTransManager getInstance() {
        if (mInstance == null) {
            mInstance = new MoTransManager();
        }
        return mInstance;
    }

    /**
     * 終了処理
     */
    public static void onDestory() {
        if (mInstance != null) {
            mInstance.finalize();
            mInstance = null;
        }
    }

    /**
     * コンストラクタ
     */
    private MoTransManager() {
        Log.i(TAG, "Constructor new Proc Array.");
        mProcArray = new SparseArray<MoBaseTransProc>();
        mFilterArray = new SparseArray<MoBaseTransFilter>();
    }

    /**
     * 初期処理
     * 
     * @throws YELibTransException
     */
    public synchronized void initialize() throws MoTransException {

        Log.i(TAG, "initialize id: " + Thread.currentThread().getId());

        for (int i = 0; i < mProcArray.size(); i++) {
            MoBaseTransProc p = mProcArray.valueAt(i);
            if (p != null) {
                p.setTransNotify(this);
                p.initialize();
            }
        }
    }

    /**
     * 終了処理
     */
    public synchronized void finalize() {

        Log.i(TAG, "finalize id: " + Thread.currentThread().getId());

        for (int i = 0; i < mProcArray.size(); i++) {
            MoBaseTransProc p = mProcArray.valueAt(i);
            if (p != null) {
                p.finalize();
                p = null;
            }
        }

        mProcArray.clear();
        mHandler = null;
    }

    /**
     * {@link Handler}設定
     * 
     * @param handler {@link Handler}
     */
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    /**
     * 通信処理クラス登録
     * 
     * @param proc {@link MoBaseTransProc}
     * @throws YELibTransException
     */
    public void resitProc(MoBaseTransProc proc) throws MoTransException {

        if (proc == null) {
            throw new MoTransException("regits MoBaseTransProc is null.");
        }

        final int id = proc.getProcId();
        if (mProcArray.get(id) != null) {
            Log.w(TAG, "MoBaseTransProc already registed. " + id);
            return;
        }

        Log.i(TAG, "trans proc regist " + id);
        mProcArray.put(id, proc);
    }

    /**
     * フィルタークラス登録
     * 
     * @param filter {@link MoBaseTransFilter}
     * @throws MoTransException
     */
    public void registReqFilter(MoBaseTransFilter filter)
            throws MoTransException {

        if (filter == null) {
            throw new MoTransException("regits MoBaseTransFilter is null.");
        }

        final int id = filter.getProcId();
        if (mFilterArray.get(id) != null) {
            Log.w(TAG, "MoBaseTransFilter already registed. " + id);
            return;
        }

        Log.i(TAG, "requst filter regist " + id);
        mFilterArray.put(id, filter);
    }

    /**
     * リクエスト処理
     * 
     * @param id 通信処理ID
     * @param param パラメータ
     * @throws MoTransException
     */
    public synchronized void request(int id, Object param, int arg1, int arg2)
            throws MoTransException {

        MoBaseTransProc p = mProcArray.get(id);
        if (p == null) {
            throw new MoTransException("trans proc not found. " + id);
        }

        byte[] dest = (byte[]) param;
        MoBaseTransFilter f = mFilterArray.get(id);
        if (f != null) {
            dest = (byte[]) f.filter(param, arg1);
        }
        p.request(dest, dest.length, arg2);
    }

    /**
     * 接続処理
     * 
     * @param id 通信処理ID
     * @param param パラメータ
     * @throws MoTransException
     */
    public synchronized void connect(int id, Object param)
            throws MoTransException {

        MoBaseTransProc p = mProcArray.get(id);
        if (p == null) {
            throw new MoTransException("trans proc not found. " + id);
        }

        p.connect(param);
    }

    /**
     * Listen時
     */
    @Override
    public void onListen(int id, Object obj) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_LISTEN, id, 0, obj)
                    .sendToTarget();
        }
    }

    /**
     * �ڑ���
     */
    @Override
    public void onConnecting(int id, Object obj) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_CONNECTING, id, 0, obj)
                    .sendToTarget();
        }
    }

    /**
     * 接続後
     */
    @Override
    public void onConnected(int id, Object obj) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_CONNECTED, id, 0, obj)
                    .sendToTarget();
        }
    }

    /**
     * 受信時
     */
    @Override
    public void onRecv(int id, Object obj, int size) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_RECEIVE, id, size, obj)
                    .sendToTarget();
        }
    }

    /**
     * 通信状態変更時
     */
    @Override
    public void onStateChanged(int id, MoTransStatus status, String message) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_STATECHANGE, id, 0,
                    new Object[] { status, message }).sendToTarget();
        }
    }

    /**
     * 切断時
     */
    @Override
    public void onDisconnect(int id) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_DISCONNECT, id, 0, null)
                    .sendToTarget();
        }
    }

    /**
     * Lost時
     */
    @Override
    public void onLost(int id) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_LOST, id, 0, null)
                    .sendToTarget();
        }
    }

    /**
     * 接続失敗時
     */
    @Override
    public void onFailed(int id) {
        if (mHandler != null) {
            mHandler.obtainMessage(TRANS_MESSAGE_FAILED, id, 0, null)
                    .sendToTarget();
        }
    }

}
