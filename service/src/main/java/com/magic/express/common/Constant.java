package com.magic.express.common;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/11/27.
 */
public class Constant {
    public static final int status = 1;//已上交款项

    public enum Type {
        X("x", "现金"),
        W("w", "微信"),
        Q("q", "欠款"),
        D("d", "代收"),
        Y("y", "月结");
        private String type;
        private String desc;

        Type(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public static Map<String, String> all = Maps.newHashMap();

        static {
            for (Type type : EnumSet.allOf(Type.class)) {
                all.put(type.type, type.desc);
            }
        }

        public static String getDesc(String type) {
            return all.get(type);
        }
    }
}
