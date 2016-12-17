package com.magic.express;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.magic.utils.qiniu.QiNiuUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Hello world!
 */
@SpringBootApplication
@RestController
@RequestMapping("/")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("Hello World!");
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object list() {
        String key = "IMG_20161114_153251.jpg";
        return QiNiuUtils.del(key);
    }

    @RequestMapping(value = "apk", method = RequestMethod.GET)
    public Object upApk() {
        String key = "app-release.apk";
        String file = "/root/phil/express/android/app/" + key;
        QiNiuUtils.del(key);
        return QiNiuUtils.upload(new File(file));
    }

    /**
     * 使用fastJson
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
