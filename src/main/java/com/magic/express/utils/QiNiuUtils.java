package com.magic.express.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/15 10:31
 *          QiNiuUtils七牛文件存储服务
 */
public class QiNiuUtils {
    private static final Logger logger=Logger.getLogger(QiNiuUtils.class);
    private static final String AK="GL98Kn8ta-9KKwRSqDi_f5Iy9Fr_hwCr2Qqz8W1D";
    private static final String SK="doDJVr6L1EYoTc4ATVuiMazdKNXoDHWoeQ050P4s";
    private static final String DOMAIN="http://ognsbr72y.bkt.clouddn.com/";
    private static final String BUCKET="express";
    private static Auth auth=Auth.create(AK, SK);
    private static UploadManager uploadManager=new UploadManager();
    
    private QiNiuUtils() {
    }
    
    /**
     * 获取上传token
     *
     * @param fileName 要上传的文件名
     * @return token
     */
    private static String getUpToken(String fileName) {
        return auth.uploadToken(BUCKET, fileName);
    }
    
    public static String upload(File file) throws RuntimeException {
        return upload(file, file.getName());
    }
    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 要保存的文件名
     * @return 下载地址
     */
    public static String upload(File file, String fileName) throws RuntimeException {
        try{
            //调用put方法上传
            Response res=uploadManager.put(file, fileName, getUpToken(fileName));
            //打印返回的信息
            logger.info(res.bodyString());
            return getDownloadUrl(fileName);
        }catch(QiniuException e){
            Response r=e.response;
            // 请求失败时打印的异常的信息
            logger.error(r.toString());
            try{
                //响应的文本信息
                logger.error(r.bodyString());
            }catch(QiniuException e1){
                //ignore
                throw new RuntimeException(e1.getMessage());
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * 获取下载连接
     *
     * @param fileName
     * @return url
     */
    public static String getDownloadUrl(String fileName) {
        //        return auth.privateDownloadUrl(DOMAIN+fileName);
        return DOMAIN+fileName;
    }
    
}
