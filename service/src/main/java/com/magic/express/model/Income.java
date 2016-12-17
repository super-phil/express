package com.magic.express.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@Data
public class Income {
    /**
     * <option value="x">现金</option>
     * <option value="y">月结</option>
     * <option value="w">微信</option>
     * <option value="q">欠款</option>
     * <option value="d">代收</option>
     */
    private long id;
    private int x;
    private int y;
    private int w;
    private int q;
    private int d;
    private Date createTime;
    private Date updateTime;
}
