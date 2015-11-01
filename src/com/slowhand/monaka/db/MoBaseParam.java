package com.slowhand.monaka.db;

import java.lang.reflect.Field;

import com.slowhand.monaka.util.MoStringUtil;
import com.slowhand.monaka.util.MoSysUtil;

/**
 * Databaseパラメータクラス
 *
 * @author slowhand0309
 */
public abstract class MoBaseParam {

    private static final String PARAM_NAME = "ParamName";
    private static final String TYPE_NAME = "TypeName";
    private static final String VALUE_NAME = "Value";
    private static final String SEPARATOR = " : ";
    private static final String COMMA = ", ";

    /**
     * コンストラクタ
     */
    public MoBaseParam() {
    }

    /**
     * 定義メンバのダンプ
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            /* パラメータ名   */
            builder.append(PARAM_NAME);
            builder.append(SEPARATOR);
            builder.append(field.getName());
            builder.append(COMMA);

            /* 型            */
            builder.append(TYPE_NAME);
            builder.append(SEPARATOR);
            builder.append(field.getType().getName());
            builder.append(COMMA);

            /* 値            */
            builder.append(VALUE_NAME);
            builder.append(SEPARATOR);

            try {
                builder.append(field.get(this));
            } catch (IllegalAccessException e) {
                builder.append(MoStringUtil.EMPTY);
            } catch (IllegalArgumentException e) {
                builder.append(MoStringUtil.EMPTY);
            }
            builder.append(COMMA);
            builder.append(MoSysUtil.getLineSeparator());
        }
        return builder.toString();
    }
}
