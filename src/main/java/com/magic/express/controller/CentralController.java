package com.magic.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.magic.express.common.Constant;
import com.magic.express.model.DTRequest;
import com.magic.express.model.DTResponse;
import com.magic.express.model.Express;
import com.magic.express.service.ExpressService;
import com.magic.utils.excel.ExcelUtils;
import com.magic.utils.qiniu.QiNiuUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/15 12:06
 *          CentralController
 */
@RestController
@RequestMapping("express")
public class CentralController {
    @Resource
    private ExpressService expressService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }
    
    @RequestMapping(value="list", method=RequestMethod.GET)
    public DTResponse list(DTRequest dtRequest, @RequestParam(required=false) String type) {
        if(StringUtils.isEmpty(type)){
            type=Constant.Type.X.name().toLowerCase();
        }
        DTResponse<Map<String,Object>> response=expressService.findByQ(dtRequest, type);
        for(Map<String,Object> map : response.getData()){
            map.put("type", Constant.Type.getDesc((String) map.get("type")));
        }
        JSONObject jo=new JSONObject();
        if(!StringUtils.isEmpty(dtRequest.getQ())){
            jo.put("q", dtRequest.getQ());
        }
        if(!StringUtils.isEmpty(type)){
            jo.put("type", type);
        }
        int start=dtRequest.getStart();
        int offset=dtRequest.getLength();
        if(start != 0){
            int begin=start;
            begin=begin-offset;
            jo.put("previous_f", begin<0 ? 0 : begin);
        }
        if(response.getData().size()>=offset){
            jo.put("next_f", start+offset);
        }
        response.setExt(jo);
        return response;
    }
    
    /**
     * 订单按类型价格统计
     *
     * @return
     */
    @RequestMapping(value="chart-type", method=RequestMethod.GET)
    public Object chartsType() {
        return getChartByType();
    }
    
    private JSONObject getChartByType() {
        List<Map<String,Object>> mapList=expressService.chartByType();
        for(Map<String,Object> map : mapList){
            map.put("type", Constant.Type.getDesc((String) map.get("type")));
        }
        JSONObject jo=new JSONObject();
        jo.put("data", mapList);
        return jo;
    }
    
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object test(@RequestParam("date") String date) {

        //        String date="20161116";
        String s = "C:\\Users\\Phil\\Desktop\\" + date + ".xlsx";
        File file = new File(s);
        if (!file.exists()) {//如果不存在
            throw new RuntimeException("文件不存在:" + s);
        }
        List<List<Object>> rows = ExcelUtils.read(file);
        List<Express> expressList = Lists.newArrayList();
        List<Object> cell;//表头
        if (rows != null && rows.size() > 0) {
            cell = rows.get(0);
            //线验证文件在不
            for (int k = 1; k < rows.size(); k++) {
                for (int j = 0; j < cell.size(); j++) {
                    String key = (String) cell.get(j);
                    String v = (String) rows.get(k).get(j);
                    switch (key) {
                        case "img":
                            String fileName = "C:\\Users\\Phil\\Desktop\\" + date + "\\IMG_" + date + "_" + v + ".jpg";
                            File f = new File(fileName);
                            if (f.exists()) {//如果存在
                                String time = date + v;
                                DateTime dateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(time);
                                System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
                            } else {
                                throw new RuntimeException("文件不存在:" + f.getName());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            for (int i = 1; i < rows.size(); i++) {
                Express express = new Express();
                for (int j = 0; j < cell.size(); j++) {
                    String k = (String) cell.get(j);
                    String v = (String) rows.get(i).get(j);
                    switch (k) {
                        case "number":
                            express.setNumber(v);
                            break;
                        case "pirce":
                            express.setPrice(v == null ? 0 : Integer.valueOf(v));
                            break;
                        case "type":
                            express.setType(v == null ? null : v.toLowerCase());
                            break;
                        case "img":
                            String fileName = "C:\\Users\\Phil\\Desktop\\" + date + "\\IMG_" + date + "_" + v + ".jpg";
                            File f = new File(fileName);
                            if (f.exists()) {//如果存在
                                String time = date + v;
                                DateTime dateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(time);
                                express.setCreateTime(dateTime.toDate());
                                express.setUrl(QiNiuUtils.upload(f));
                                //express.setUrl("http://ognsbr72y.bkt.clouddn.com/"+f.getName());
                            } else {
                                throw new RuntimeException("文件不存在:" + f.getName());
                            }
                            break;
                        default:
                            break;
                    }
                }
                expressList.add(express);
            }
        }
        expressService.save(expressList);
        return "成功!";
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
    @RequestMapping(value="del", method=RequestMethod.POST)
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
    @RequestMapping(value="yes", method=RequestMethod.POST)
    public Object yes(@RequestParam() String id) {
        expressService.updateTypeToX(id);
        return getChartByType();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(Express express) {
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

    public static void main(String[] args) {
        System.out.println(Constant.Type.X.name().toLowerCase());
    }

}
