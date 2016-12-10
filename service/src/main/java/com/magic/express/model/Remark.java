package com.magic.express.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */
@Data
public class Remark implements Serializable{
    private static final long serialVersionUID = 8612338914416662714L;
    private long id;
    private String text;
}
