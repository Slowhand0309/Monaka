package com.slowhand.monaka.util;

/**
 * Stringユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoStringUtil {

    /** 空文字 */
    public static final String EMPTY = "";

    /**
     * 空判定
     * 
     * @param value 判定値
     * @return true : 空かnull false : 空でない
     */
    public static boolean isEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * NOT空判定
     * 
     * @param value 設定ち
     * @return true : 空でない false : 空かnull
     */
    public static boolean isNotEmpty(String value) {
        return !MoStringUtil.isEmpty(value);
    }

    /**
     * 空白判定
     * 
     * @param value 判定値
     * @return true : 空白 false : 空白でない
     */
    public static boolean isBlank(String value) {

        if (MoStringUtil.isEmpty(value)) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     *  NOT空白判定
     * 
     * @param value 判定値
     * @return true : 空白でない false : 空白
     */
    public static boolean isNotBlank(String value) {
        return !MoStringUtil.isBlank(value);
    }

    /**
     * 文字列が含まれているか判定
     * 
     * @param value 判定値
     * @param at 文字列
     * @return true : 含まれている false : 含まれていない
     */
    public static boolean contains(String value, String at) {
        if (MoStringUtil.isEmpty(value) || MoStringUtil.isEmpty(at)) {
            return false;
        }
        return value.indexOf(at) >= 0;
    }
}
