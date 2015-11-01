package com.slowhand.monaka.trans.base;

/**
 * パケット解析クラス
 * 
 * @author slowhand0309
 */
public abstract class MoBaseTransAnalyzer {

    protected int mProcId;

    /**
     * 解析処理
     * 
     * @param obj データ
     * @param size データサイズ
     * @return 解析後データ
     */
    public abstract Object analyze(Object obj, int size);

    /**
     * コンストラクタ
     * 
     * @param procId 通信処理ID
     */
    public MoBaseTransAnalyzer(int procId) {
        mProcId = procId;
    }

    /**
     * 通信処理IDの取得
     * 
     * @return 通信処理ID
     */
    public int getProcId() {
        return mProcId;
    }
}
