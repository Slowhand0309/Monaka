package com.slowhand.monaka.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * {@link Context}ユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoContextUtil {

    /**
     * 指定パッケージの{@link Context}取得
     * 
     * @param context {@link Context}
     * @param packageName パッケージ名
     * @return パッケージの{@link Context} <br>
     *         失敗した場合はnull
     * @throws NameNotFoundException
     */
    public static Context getPackageContext(Context context, String packageName)
            throws NameNotFoundException {

        if (context == null || MoStringUtil.isEmpty(packageName)) {
            return null;
        }

        return context.createPackageContext(packageName, 0);
    }

    /**
     * 指定したパッケージの{@link SharedPreferences}取得
     * 
     * @param context {@link Context}
     * @param packageName パッケージ名
     * @param prefName ぷリファレンス名
     * @return {@link SharedPreferences}
     * @throws NameNotFoundException
     */
    public static SharedPreferences getPackagePreferences(Context context,
            String packageName, String prefName) throws NameNotFoundException {

        Context packageContext = MoContextUtil.getPackageContext(context,
                packageName);
        if (packageContext == null) {
            return null;
        }
        return packageContext.getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
    }

    /**
     * リソースIDの取得
     * 
     * @param context {@link Context}
     * @param name リソース名
     * @param type リソースタプ
     * @return リソースID <br>
     *         失敗した場合は0
     */
    public static int getResourceId(Context context, String name, String type) {

        if (context == null || MoStringUtil.isEmpty(name)
                || MoStringUtil.isEmpty(type)) {
            return 0;
        }
        return context.getResources().getIdentifier(name, type,
                context.getPackageName());
    }

    /**
     * アプリケーション名の取得
     * 
     * @param context {@link Context}
     * @return アプリケーション名
     */
    public static String getApplicationName(Context context) {

        if (context == null) {
            return "";
        }

        String name = "";
        PackageManager pm = context.getPackageManager();
        if (pm != null) {

            try {
                /* パッケージ情報の取得 */
                PackageInfo info = pm.getPackageInfo(context.getPackageName(),
                        0);
                ApplicationInfo appInfo = info.applicationInfo;
                if (appInfo != null) {
                    name = (String) appInfo.loadLabel(pm);
                }
            } catch (NameNotFoundException e) {

            }
        }
        return name;
    }
}
