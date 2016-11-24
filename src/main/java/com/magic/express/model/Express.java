package com.magic.express.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/16 11:18
 *          Express 快递
 */
@Data
@Table(name="express")
@Entity
public class Express  implements Serializable {
    private static final long serialVersionUID=8612338914410162714L;
    @Id
    @GeneratedValue
    private long id;
    @Column(name="number", nullable=false, length=32)//单号
    private String number;
    @Column(name="type",length=32)//类型
    private String type;
    @Column(name="price", nullable=false)//价格
    private int price;
    @Column(name="url")
    private String url;
    @Column(name="create_time")
    private Date createTime;
    
    @JSONField(serialize=false)//前台不显示
    @Column(name="update_time")
    private Date updateTime;
}
