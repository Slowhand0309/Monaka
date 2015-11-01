package com.slowhand.monaka.trans;

/**
 * 通信状態
 * 
 * @author slowhand0309
 */
public enum MoTransStatus {

    TRANS_STATUS_NONE, /* 通信なし */
    TRANS_STATUS_LISTEN, /* Listen状態 */
    TRANS_STATUS_CONNECTING, /* 接続中 */
    TRANS_STATUS_CONNECTED, /* 接続完了 */
    TRANS_STATUS_TIMEOUT, /* タイムアウト */
    TRANS_STATUS_FAILED, /* 接続失敗 */
    TRANS_STATUS_LOST, /* 接続ロスト */
    TRANS_STATUS_DISCONNECT /* 接続断 */
}
