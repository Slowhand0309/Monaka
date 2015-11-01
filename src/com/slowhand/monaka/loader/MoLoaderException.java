package com.slowhand.monaka.loader;

/**
 * {@link Loader}例外クラス
 *
 * @see Exception
 * @author slowhand0309
 */
public class MoLoaderException extends Exception {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -4737083627123230893L;

    /**
     * コンストラクタ
     *
     * @param detailMessage
     */
    public MoLoaderException(String detailMessage) {
        super(detailMessage);
    }

}
