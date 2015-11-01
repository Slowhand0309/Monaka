/**
 *
 */
package com.slowhand.monaka.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Drawableユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoDrawableUtil {

    /* 回転方向定義` */
    public enum InvertState {
        INVERTSTATE_UD, /* 上下 */
        INVERTSTATE_LR, /* 左右 */
        INVERTSTATE_UPLR /* 上下左右 */
    }

    /**
     * {@link Drawable}の回転処理
     * 
     * @param context {@link Context}
     * @param d {@link Drawable}
     * @param state {@link InvertState}
     * @return {@link Drawable}
     * @throws IllegalArgumentException
     */
    public static Drawable invertDrawable(Context context, Drawable d,
            InvertState state) throws IllegalArgumentException {

        if (context == null || d == null) {
            throw new IllegalArgumentException("invalid argument's null.");
        }

        Bitmap src = toBitmap(d);
        if (src == null) {
            throw new IllegalArgumentException(
                    "convert failed drawable to bitmap.");
        }

        Bitmap dest = invertDrawable(src, state);
        return toDrawable(context.getResources(), dest);
    }

    /**
     * 回転処理
     * 
     * @param bitmap {@link Bitmap}
     * @param state {@link InvertState}
     * @return {@link Bitmap}
     * @throws YELibValidateException
     */
    public static Bitmap invertDrawable(Bitmap bitmap, InvertState state)
            throws IllegalArgumentException {

        Bitmap result = null;
        if (bitmap == null) {
            throw new IllegalArgumentException("invalid arg bitmap is null.");
        }

        float sx = 1.0f;
        float sy = 1.0f;
        Matrix mat = new Matrix();
        /* 回転方向 */
        switch (state) {
            case INVERTSTATE_LR:
                sx = -1.0f;
                break;
            case INVERTSTATE_UD:
                sy = -1.0f;
                break;
            case INVERTSTATE_UPLR:
                sx = -1.0f;
                sy = -1.0f;
                break;
            default:
                break;
        }

        mat.preScale(sx, sy);
        result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), mat, false);

        return result;
    }

    /**
     * {@link Bitmap}から{@link Drawable}へ変換
     * 
     * @param res {@link Resources}
     * @param bitmap {@link Bitmap}
     * @return {@link Drawable}
     */
    public static Drawable toDrawable(Resources res, Bitmap bitmap) {
        if (res == null || bitmap == null) {
            return null;
        }
        Drawable d = new BitmapDrawable(res, bitmap);
        return d;
    }

    /**
     * {@link Drawable}から{@link Bitmap}へ変換
     * 
     * @param d {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap toBitmap(Drawable d) {

        Bitmap bitmap = null;
        if (d != null) {
            if (d instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) d;
                bitmap = bd.getBitmap();
            }
        }
        return bitmap;
    }
}
