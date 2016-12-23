package com.magic.express;

import android.app.Application;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by ZhaoXiuFei on 2016/12/3.
 */

public class App extends Application {

    public static final String DOMAIN = "http://123.56.102.224:17051";
    public static final String EXPRESS_IMAGE = "express_image";
    public static final String USER = "user";

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
