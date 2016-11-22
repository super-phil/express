package com.magic.express.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/14 17:25
 *          GlobalExceptionHandler 全局异常拦截
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value=Exception.class)
    public Object defaultErrorHandler(Exception e) {
       /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：@ResponseBody
        * 添加return即可。
        */
       
       /*
        * 返回视图：
        * 定义一个ModelAndView即可，
        * 然后return;
        * 定义视图文件(比如：exception.html,exception.ftl,exception.jsp);
        *
        */
       
        JSONObject object = new JSONObject();
        object.put("code",-1);
        object.put("msg",e.getMessage());
       return object;
    }
}
