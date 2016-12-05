package com.magic.express.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 10:22
 *          DTResponse
 */
@Data
public class DTResponse <T> implements Serializable {
    private static final long serialVersionUID=-7695090604534808243L;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;
    private Object ext;//扩展数据
    public void setData(List<T> data) {
        this.data = data == null ? new ArrayList<T>(0) : data;
    }
}
