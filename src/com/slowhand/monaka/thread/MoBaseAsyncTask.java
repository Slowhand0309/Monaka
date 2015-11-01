package com.slowhand.monaka.thread;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/**
 * 非同期タスク基底クラス
 * 
 * @author slowhand0309
 */
public abstract class MoBaseAsyncTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> implements OnCancelListener {

    private static final String TAG = MoBaseAsyncTask.class.getSimpleName();

    private int mTimeout = -1;
    private final AtomicBoolean mLoop = new AtomicBoolean(true);
    protected Context mContext;
    protected ProgressDialog mDialog;

    /**
     * {@link ProgressDialog}設定<br>
     * 
     * @param dialog {@link ProgressDialog}
     */
    protected abstract void onDialogSetting(ProgressDialog dialog);

    /**
     * 処理実行
     * 
     * @param params パラメータ
     * @return 結果
     */
    protected abstract Result onLoop(Params... params);

    /**
     * コンストラクタ
     * 
     * @param context {@link Context}
     */
    public MoBaseAsyncTask(Context context) {
        mContext = context;
    }

    /**
     * タイムアウト設定
     * 
     * @param timeout タイムアウト値
     */
    public void setTimeout(int timeout) {
        mTimeout = timeout;
    }

    /**
     * ループ有無
     * 
     * @param enabled true ループ false 否ループ
     */
    public void setLoopEnabled(boolean enabled) {
        mLoop.set(enabled);
    }

    /**
     * 実行前処理
     */
    @Override
    protected void onPreExecute() {

        mDialog = new ProgressDialog(mContext);
        onDialogSetting(mDialog);
        mDialog.show();

        if (mTimeout != -1) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    synchronized (this) {
                        if (mLoop.get()) {
                            mLoop.set(false);
                            onTimeout();

                            Log.w(TAG, "async task timeout. loop flag => "
                                    + mLoop);
                        }
                    }
                }
            }, mTimeout);
        }
    }

    /**
     * バックグラウンド処理
     */
    @Override
    protected Result doInBackground(Params... params) {

        Result ret = null;
        while (mLoop.get()) {
            synchronized (this) {
                ret = onLoop(params);
            }
        }
        return ret;
    }

    /**
     * 実行後処理
     */
    @Override
    protected void onPostExecute(Result result) {
        mDialog.dismiss();
    }

    /**
     * キャンセル時処理
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        this.cancel(true);
    }

    /**
     * キャンセル後処理
     */
    @Override
    protected void onCancelled() {
        mDialog.dismiss();
    }

    /**
     * タイムアウト時
     */
    protected void onTimeout() {
    }
}
