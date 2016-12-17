package com.magic.express.model;

import java.util.Date;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
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
    private Date create_time;
    private Date update_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
