package com.magic.express.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 10:20
 *          DTRequest
 */
@Data
public class DTRequest implements Serializable {
    private static final long serialVersionUID = 7952081428830565475L;
    private int draw;
    private int start;//开始
    private int length = 20;//默认长度
    private String q;//查询参数

    public String getQ() {
        return q == null ? "" : q.trim();
    }
}
