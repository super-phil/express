package com.magic.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.magic.express.common.Constant;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import com.magic.express.service.ExpressService;
import com.magic.utils.qiniu.QiNiuUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/15 12:06
 *          CentralController
 */
@RestController
@RequestMapping("express")
public class ExpressController {
    @Resource
    private ExpressService expressService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public DTResponse list(DTRequest dtRequest, @RequestParam(required = false) String type) {
        if (StringUtils.isEmpty(type)) {
            type = Constant.Type.X.name().toLowerCase();
        }
        DTResponse<Map<String, Object>> response = expressService.findByQ(dtRequest, type);
        for (Map<String, Object> map : response.getData()) {
            map.put("type", Constant.Type.getDesc((String) map.get("type")));
        }
        JSONObject jo = new JSONObject();
        if (!StringUtils.isEmpty(dtRequest.getQ())) {
            jo.put("q", dtRequest.getQ());
        }
        if (!StringUtils.isEmpty(type)) {
            jo.put("type", type);
        }
        int start = dtRequest.getStart();
        int offset = dtRequest.getLength();
        if (start != 0) {
            int begin = start;
            begin = begin - offset;
            jo.put("previous_f", begin < 0 ? 0 : begin);
        }
        if (response.getData().size() >= offset) {
            jo.put("next_f", start + offset);
        }
        response.setExt(jo);
        return response;
    }

    /**
     * 订单按类型价格统计
     *
     * @return
     */
    @RequestMapping(value = "chart-type", method = RequestMethod.GET)
    public Object chartsType() {
        return getChartByType();
    }

    private JSONObject getChartByType() {
        List<Map<String, Object>> mapList = expressService.chartByType();
        for (Map<String, Object> map : mapList) {
            map.put("type", Constant.Type.getDesc((String) map.get("type")));
        }
        JSONObject jo = new JSONObject();
        jo.put("data", mapList);
        return jo;
    }

    @RequestMapping("/chart")
    public ModelAndView chart() {
        return new ModelAndView("charts");
    }

    /**
     * 删除
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public Object del(@RequestParam() String id) {
        expressService.del(id);
        return getChartByType();
    }

    /**
     * 变更欠款到现金
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "yes", method = RequestMethod.POST)
    public Object yes(@RequestParam() String id) {
        expressService.updateTypeToX(id);
        return getChartByType();
    }

    /**
     * 上交款项
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "up", method = RequestMethod.POST)
    public Object up(@RequestParam() String id) {
        return expressService.updateStatus(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(Express express) {
        Date date = new Date();
        express.setCreateTime(date);
        express.setUpdateTime(date);
        expressService.save(express);
        return getChartByType();
    }

    /**
     * 订单价格统计
     *
     * @return
     */
    @RequestMapping(value = "charts/price", method = RequestMethod.GET)
    public Object chartsPrice(@RequestParam(defaultValue = "7") int days) {
        List<Map<String, Object>> mapList = expressService.chartsPrice(days);
        List<Integer> sum = Lists.newArrayList();
        List<Integer> count = Lists.newArrayList();
        List<String> categories = Lists.newArrayList();
        for (Map<String, Object> map : mapList) {
            sum.add(Integer.valueOf(map.get("sum").toString()));
            count.add(Integer.valueOf(map.get("count").toString()));
            categories.add(map.get("create_time").toString());
        }
        JSONObject jo = new JSONObject();
        jo.put("sum", sum);
        jo.put("count", count);
        jo.put("categories", categories);
        return jo;
    }

    /**
     * 上交款项
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(@RequestParam String id, @RequestParam int price, @RequestParam String type, @RequestParam String desc) {
        return expressService.updateInfo(id, price, type, desc);
    }

    /**
     * 获取token
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "token", method = RequestMethod.POST)
    public Object token(@RequestParam String key) {
        return QiNiuUtils.getUpToken(key);
    }

    /**
     * 更新url
     *
     * @param number number
     * @param url    url
     * @return
     */
    @RequestMapping(value = "upload/url", method = RequestMethod.POST)
    public Object uploadUrl(@RequestParam String number, @RequestParam String url) {
        return expressService.updateUrl(number,url);
    }

    public static void main(String[] args) {
        System.out.println(Constant.Type.X.name().toLowerCase());
    }

}
