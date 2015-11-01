package com.slowhand.monaka.trans;

/**
 * 通信時の例外クラス
 * 
 * @author slowhand0309
 */
public class MoTransException extends Exception {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 2475440999764299906L;

    /**
     * コンストラクタ
     * 
     * @param detailMessage メッセージ
     */
    public MoTransException(String detailMessage) {
        super(detailMessage);
    }
}
