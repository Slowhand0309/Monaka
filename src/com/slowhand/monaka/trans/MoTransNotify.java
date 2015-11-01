package com.slowhand.monaka.trans;

/**
 * 通知クラス
 * 
 * @author slowhand0309
 */
public interface MoTransNotify {

    /**
     * Listen時
     * 
     * @apram id 通信処理ID
     * @param obj パラメータ
     */
    public void onListen(int id, Object obj);

    /**
     * 接続中
     * 
     * @apram id 通信処理ID
     * @param obj パラメータ
     */
    public void onConnecting(int id, Object obj);

    /**
     * 接続後
     * 
     * @apram id 通信処理ID
     * @param obj パラメータ
     */
    public void onConnected(int id, Object obj);

    /**
     * 受診時
     * 
     * @param id 通信処理ID
     * @param obj パラメータ
     * @param size データサイズ
     */
    public void onRecv(int id, Object obj, int size);

    /**
     * 通信状態変更時
     * 
     * @apram id 通信処理ID
     * @param status {@link MoTransStatus}
     * @param message メッセージ
     */
    public void onStateChanged(int id, MoTransStatus status, String message);

    /**
     * 接続Lost時
     * 
     * @param id 通信処理ID
     */
    public void onLost(int id);

    /**
     * 終了処理
     * 
     * @param id 通信処理ID
     */
    public void onFailed(int id);

    /**
     * 切断時
     * 
     * @apram id 通信処理ID
     */
    public void onDisconnect(int id);
}
