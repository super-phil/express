package com.magic.express;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */

public class Utils {

    public static final UploadManager uploadManager;

    private Utils() {
    }

    static {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(10 * 1024 * 1024)  // 启用分片上传阀值。默认5120K
                .connectTimeout(60) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
//                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    /**
     * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static final String QINIU_DOMAIN = "http://ognsbr72y.bkt.clouddn.com/";
    //七牛后台的key
    private static String AccessKey = "GL98Kn8ta-9KKwRSqDi_f5Iy9Fr_hwCr2Qqz8W1D";
    //七牛后台的secret
    private static String SecretKey = "doDJVr6L1EYoTc4ATVuiMazdKNXoDHWoeQ050P4s";
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final String BUCKET_NAME = "express";

    public static String getToken() {
        // 1:第一种方式 构造上传策略
        JSONObject _json = new JSONObject();
        long _deadline = System.currentTimeMillis() / 1000 + 3600;
        _json.put("deadline", _deadline);// 有效时间为一个小时
        _json.put("scope", BUCKET_NAME);
        String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                .toString().getBytes());
        byte[] _sign = new byte[0];
        try {
            _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String _encodedSign = UrlSafeBase64.encodeToString(_sign);
        return String.format("%s:%s:%s", AccessKey, _encodedSign, _encodedPutPolicy);
    }

    private static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public static String format(long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(time));
    }

    /**
     * 是否wifi环境
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //只返回图片的大小信息
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
}
