package com.magic.express.model;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/12/4.
 */

public enum Type {
    X("x", "现金"),
    W("w", "微信"),
    Q("q", "欠款"),
    D("d", "代收"),
    Y("y", "月结");
    private String code;
    private String desc;

    Type(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Map<String, String> codeDesc = Maps.newHashMap();
    public static Map<String, String> descCode = Maps.newHashMap();

    static {
        for (Type type : EnumSet.allOf(Type.class)) {
            codeDesc.put(type.code, type.desc);
            descCode.put(type.desc, type.code);
        }
    }

    public static String getDesc(String code) {
        return codeDesc.get(code);
    }

    public static String getCode(String desc) {
        return descCode.get(desc);
    }
}
