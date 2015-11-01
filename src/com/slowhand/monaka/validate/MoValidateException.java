package com.slowhand.monaka.validate;

/**
 * Validate例外クラス
 *
 * @see Exception
 * @author slowhand0309
 */
public class MoValidateException extends Exception {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 7139262858330989648L;

    /**
     * コンストラクタ
     *
     * @param detailMessage
     */
    public MoValidateException(String detailMessage) {
        super(detailMessage);
    }

}
