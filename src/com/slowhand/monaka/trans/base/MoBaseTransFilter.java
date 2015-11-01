package com.slowhand.monaka.trans.base;

/**
 * パケットフィルター基底クラス
 * 
 * @author slowhand0309
 */
public abstract class MoBaseTransFilter {

    protected int mProcId;

    /**
     * コンストラクタ
     */
    public MoBaseTransFilter(int procId) {
        mProcId = procId;
    }

    /**
     * フィルター実施
     * 
     * @param obj パラメータ
     * @param length サイズ
     * @return フィルター後データ
     */
    public abstract Object filter(Object obj, int length);

    /**
     * 通信処理IDを取得
     * 
     * @return 通信処理ID
     */
    public int getProcId() {
        return mProcId;
    }
}
