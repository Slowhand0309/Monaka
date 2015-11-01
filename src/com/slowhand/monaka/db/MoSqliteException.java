package com.slowhand.monaka.db;

/**
 * SQLiteに関する例外クラス
 *
 * @see Exception
 * @author slowhand0309
 */
public class MoSqliteException extends Exception {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = -4094901640123730183L;

    /**
     * コンストラクタ
     *
     * @param detailMessage
     */
    public MoSqliteException(String detailMessage) {
        super(detailMessage);
    }

}
