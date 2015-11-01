package com.slowhand.monaka.view;

import java.util.List;

import com.slowhand.monaka.log.MoAppJournalLog;
import com.slowhand.monaka.log.MoJournalLogManager;
import com.slowhand.monaka.util.MoStringUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 基底Activityクラス<br>
 * android.support.v4.app.FragmentActivity
 *
 * @author slowhand0309
 */
public abstract class MoBaseActivity extends FragmentActivity {

    private static final String TAG = MoBaseActivity.class.getSimpleName();

    private MoAppJournalLog mAppLog;

    /**
     * Activity起動
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        /* LogManager取得 */
        MoJournalLogManager lm
            = MoJournalLogManager.getInstance(getApplicationContext());

        mAppLog = lm.getAppJournalLog();
        mAppLog.outputLog(TAG, this.getClass().getSimpleName() + "#onCreate");
    }

    /**
     * Activity終了
     */
    @Override
    protected void onDestroy() {
        mAppLog.outputLog(TAG, this.getClass().getSimpleName() + "#onDestory");
        super.onDestroy();
    }

    /**
     * アプリケーションジャーナルログ出力
     *
     * @param __LEVEL__ 出力レベル
     * @param value 値
     */
    protected void outputAppLog(String __LEVEL__, String value) {
        if (MoStringUtil.isNotEmpty(value)) {
            mAppLog.outputLog(__LEVEL__, value);
        }
    }

    /**
     * アプリケーションジャーナルログ出力
     * <br>strings.xmlのリソースID指定
     *
     * @param __LEVEL__ 出力レベル
     * @param resId strings.xmlのリソースID
     * @param args 引数
     */
    protected void outputAppLog(String __LEVEL__, int resId, Object ... args) {

        String format = getString(resId);
        if (MoStringUtil.isNotEmpty(format)) {
            String message = format;
            if (args != null) {
                message = String.format(format, args);
            }
            outputAppLog(__LEVEL__, message);
        }
    }

    /**
     * アプリケーションジャーナルログ出力
     *
     * @param __LEVEL__ 出力レベル
     * @param values 値
     */
    protected void outputAppLog(String __LEVEL__, List<String> values) {
        for (String value : values) {
            outputAppLog(__LEVEL__, value);
        }
    }

    /**
     * 表示可否
     *
     * @param id リソースID
     * @param visible VISIBLE, INVISIBLE, GONE
     */
    protected void setVisible(int id, int visible) {
        View view = findViewById(id);
        if (view != null) {
            view.setVisibility(visible);
        }
    }

    /**
     * 有効/無効判定
     *
     * @param id リソースID
     * @param enabled true : 有効 false : 無効
     */
    protected void setEnabled(int id, boolean enabled) {
        View view = findViewById(id);
        if (view != null) {
            view.setEnabled(enabled);
        }
    }

    /**
     * テキスト設定
     *
     * @param id リソースID
     * @param value 値
     */
    protected void setText(int id, String value) {
        if (MoStringUtil.isEmpty(value)) {
            return;
        }
        View view = findViewById(id);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(value);
        }
    }

    /**
     * {@link TextView}空判定
     *
     * @param id リソースID
     * @return true : 空 / false : 空でない
     */
    protected boolean isTextEmpty(int id) {
        View view = findViewById(id);
        if (view != null && view instanceof TextView) {
            String value = (String) ((TextView) view).getText();
            if (MoStringUtil.isNotEmpty(value)) {
                /* TextViewの設定文字が空   */
                return false;
            }
        }
        return true;
    }

    /**
     * {@link EditText}へエラーメッセージ設定
     *
     * @param id リソースID
     * @param message 設定メッセージ
     */
    protected void setErrorMsg(int id, String message) {
        View view = findViewById(id);
        if (view instanceof EditText) {
            setErrorMsg((EditText) view, message);
        }
    }

    /**
     * {@link EditText}へエラーメッセージ設定
     *
     * @param edit {@link EditText}
     * @param message エラーメッセージ
     */
    protected void setErrorMsg(EditText edit, String message) {
        if (edit != null && message != null) {
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            edit.setError(message);
        }
    }

    /**
     * {@link EditText}からエラーメッセージクリア
     *
     * @param id リソースID
     */
    protected void clearErrorMsg(int id) {
        View view = findViewById(id);
        if (view instanceof EditText) {
            clearErrorMsg((EditText) view);
        }
    }

    /**
     * {@link EditText}からエラーメッセージクリア
     *
     * @param edit {@link EditText}
     */
    protected void clearErrorMsg(EditText edit) {
        if (edit != null) {
            edit.setFocusableInTouchMode(false);
            edit.setError(null);
            edit.clearFocus();
        }
    }
}
