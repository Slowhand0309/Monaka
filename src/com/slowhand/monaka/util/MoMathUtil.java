package com.slowhand.monaka.util;

/**
 * Mathユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoMathUtil {

    /**
     * long変換処理
     * 
     * @param data1 データ1
     * @param data2 データ2
     * @param data3 データ3
     * @param data4 データ4
     * @return long値
     */
    public static long toLong(byte data1, byte data2, byte data3, byte data4) {

        long ldata = 0;
        short sData1;
        short sData2;
        short sData3;
        short sData4;

        sData1 = (short) ((short) data1 & 0xff);
        sData2 = (short) ((short) data2 & 0xff);
        sData3 = (short) ((short) data3 & 0xff);
        sData4 = (short) ((short) data4 & 0xff);

        ldata = (long) (sData1 << 24) | (long) (sData2 << 16)
                | (long) (sData3 << 8) | (long) sData4;

        return ldata;
    }

    /**
     * integerへ変換
     * 
     * @param data1 データ1
     * @param data2 データ2
     * @return integer値
     */
    public static int toInt(byte data1, byte data2) {

        int sdata = 0;

        short sData1;
        short sData2;

        sData1 = (short) ((short) data1 & 0xff);
        sData2 = (short) ((short) data2 & 0xff);

        sdata = (int) ((int) (sData1 << 8) | (int) sData2);

        return sdata;
    }

    /**
     * 値が範囲内かチェック
     * 
     * @param amount 比較値
     * @param low 最小値
     * @param high 最大値
     * @return 比較結果
     */
    public static float contains(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    /**
     * 指定された値を分割
     * 
     * @param val 値
     * @return 分割値
     */
    public static int[] equalitySqure(int val) {
        double d = Math.sqrt(val);
        int h = (int) Math.round(d);
        int w = (int) Math.ceil(d);
        return new int[] { h, w };
    }
}
