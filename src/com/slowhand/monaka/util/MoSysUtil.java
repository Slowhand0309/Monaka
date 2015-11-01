package com.slowhand.monaka.util;

/**
 * システムユーティリティクラス
 * 
 * @author slowhand0309
 */
public final class MoSysUtil {

    public static final String PKEY_OS_NAME = "os.name";
    public static final String PKEY_OS_VERSION = "os.version";
    public static final String PKEY_OS_ARCH = "os.arch";

    public static final String PKEY_VM_NAME = "java.vm.name";
    public static final String PKEY_VM_VERSION = "java.vm.version";

    public static final String PKEY_FILE_ENCODING = "file.encoding";
    public static final String PKEY_FILE_SEPARATOR = "file.separator";
    public static final String PKEY_LINE_SEPARATOR = "line.separator";

    public static final String PKEY_USER_LANGUAGE = "user.language";

    /** デフォルト改行コード */
    private static final String DEFAULT_LINE_SEPARATOR = "\n";

    /** デフォルトのディレクトリセパレータコード */
    private static final String DEFAULT_FILE_SEPARATOR = "/";

    /**
     * system.propertiesから改行コード取得
     * 
     * @return 改行コード
     */
    public static String getLineSeparator() {
        return System.getProperty(PKEY_LINE_SEPARATOR, DEFAULT_LINE_SEPARATOR);
    }

    /**
     * system.propertiesからファイルセパレータ取得
     * 
     * @return ファイルセパレータ
     */
    public static String getFileSeparator() {
        return System.getProperty(PKEY_FILE_SEPARATOR, DEFAULT_FILE_SEPARATOR);
    }

    /**
     * system.propertiesから指定キーの値を取得<br>
     * 
     * @param key キー
     * @return 値
     */
    public static String getProperty(String key) {
        return System.getProperty(key, MoStringUtil.EMPTY);
    }

    /**
     * システム情報
     * 
     * @return 文字列
     */
    public static String dump() {

        StringBuilder builder = new StringBuilder();

        /* OS */
        builder.append("OS NAME : ");
        builder.append(MoSysUtil.getProperty(PKEY_OS_NAME));
        builder.append(MoSysUtil.getLineSeparator());

        builder.append("OS VERSION : ");
        builder.append(MoSysUtil.getProperty(PKEY_OS_VERSION));
        builder.append(MoSysUtil.getLineSeparator());

        builder.append("OS ARCHITECTURE : ");
        builder.append(MoSysUtil.getProperty(PKEY_OS_ARCH));
        builder.append(MoSysUtil.getLineSeparator());

        /* VM */
        builder.append("VM NAME : ");
        builder.append(MoSysUtil.getProperty(PKEY_VM_NAME));
        builder.append(MoSysUtil.getLineSeparator());

        builder.append("VM VERSION : ");
        builder.append(MoSysUtil.getProperty(PKEY_VM_VERSION));
        builder.append(MoSysUtil.getLineSeparator());

        /* IO */
        builder.append("FILE ENCODING : ");
        builder.append(MoSysUtil.getProperty(PKEY_FILE_ENCODING));
        builder.append(MoSysUtil.getLineSeparator());

        builder.append("FILE SEPARATOR : ");
        builder.append(MoSysUtil.getProperty(PKEY_FILE_SEPARATOR));
        builder.append(MoSysUtil.getLineSeparator());

        builder.append("LINE SEPARATOR : ");
        builder.append(MoSysUtil.getProperty(PKEY_LINE_SEPARATOR));
        builder.append(MoSysUtil.getLineSeparator());

        /* USER */
        builder.append("USER LANGUAGE : ");
        builder.append(MoSysUtil.getProperty(PKEY_USER_LANGUAGE));
        builder.append(MoSysUtil.getLineSeparator());

        return builder.toString();
    }
}
