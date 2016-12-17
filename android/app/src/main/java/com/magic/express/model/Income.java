package com.magic.express.model;

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
    private String x;
    private String y;
    private String w;
    private String q;
    private String d;
    private long create_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
