package com.magic.express.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 11:18
 *          Express 快递
 */
@Data
public class Express implements Serializable {
    private static final long serialVersionUID = 8612338914410162714L;
    private long id;
    private String number;
    /**
     * <option value="x">现金</option>
     * <option value="y">月结</option>
     * <option value="w">微信</option>
     * <option value="q">欠款</option>
     * <option value="d">代收</option>
     */
    private String type;
    private int price;
    @JSONField(serialize = false)//前台不显示
    private String url;
    private Date createTime;
    @JSONField(serialize = false)//前台不显示
    private Date updateTime;
}
