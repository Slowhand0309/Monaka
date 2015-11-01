package com.slowhand.monaka.trans.base;

import com.slowhand.monaka.trans.MoTransException;
import com.slowhand.monaka.trans.MoTransNotify;
import com.slowhand.monaka.trans.MoTransStatus;

/**
 * 通信処理基底クラス
 *
 * @author slowhand0309
 */
public abstract class MoBaseTransProc {

    protected int mProcId;
    protected MoTransNotify mTransNotify;
    protected MoTransStatus mStatus = MoTransStatus.TRANS_STATUS_NONE;

    /**
     * 初期処理
     *
     * @throws MoTransException
     */
    public abstract void initialize() throws MoTransException;

    /**
     * 終了処理
     */
    public abstract void finalize();

    /**
     * リクエスト処理
     *
     * @param param パラメータ
     * @throws MoTransException
     */
    public abstract void request(Object param, int arg1, int arg2)
            throws MoTransException;

    /**
     * 接続処理
     *
     * @param param パラメータ
     * @throws MoTransException
     */
    public abstract void connect(Object param) throws MoTransException;

    /**
     * コンストラクタ
     *
     * @param procId ID
     */
    public MoBaseTransProc(int procId) {
        mProcId = procId;
    }

    /**
     * IDの取得
     *
     * @return ID
     */
    public int getProcId() {
        return mProcId;
    }

    /**
     * 通知インターフェースの登録
     *
     * @param notify {@link MoTransNotify}
     */
    public void setTransNotify(MoTransNotify notify) {
        mTransNotify = notify;
    }

    /**
     * 状態通知
     *
     * @param message メッセージ
     */
    public void notifyState(MoTransStatus status, String message, Object obj) {
        mStatus = status;
        if (mTransNotify != null) {

            /* 状態 */
            switch (mStatus) {
                case TRANS_STATUS_LISTEN:
                    mTransNotify.onListen(mProcId, obj);
                    break;

                case TRANS_STATUS_CONNECTING:
                    mTransNotify.onConnecting(mProcId, obj);
                    break;

                case TRANS_STATUS_CONNECTED:
                    mTransNotify.onConnected(mProcId, obj);
                    break;

                case TRANS_STATUS_FAILED:
                    mTransNotify.onFailed(mProcId);
                    break;

                case TRANS_STATUS_LOST:
                    mTransNotify.onLost(mProcId);
                    break;

                case TRANS_STATUS_DISCONNECT:
                    mTransNotify.onDisconnect(mProcId);
                    break;
                default:
                    break;
            }

            /* 通知 */
            mTransNotify.onStateChanged(mProcId, mStatus, message);
        }
    }
}
