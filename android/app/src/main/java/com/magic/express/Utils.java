package com.magic.express;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */

public class Utils {

    private Utils(){}
    public static String format(long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(time));
    }
}
