package com.magic.express.exception;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/22 15:21
 *          BusinessException
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }
    
    public BusinessException(String message) {
        super(message);
    }
}
