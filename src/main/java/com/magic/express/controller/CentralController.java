package com.magic.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.magic.express.model.DTRequest;
import com.magic.express.model.Express;
import com.magic.express.service.ExpressService;
import com.magic.utils.excel.ExcelUtils;
import com.magic.utils.qiniu.QiNiuUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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
    
    @RequestMapping(value="test", method=RequestMethod.GET)
    public Object test(@RequestParam("date") String date) {
    
        //        String date="20161116";
        String s="C:\\Users\\Phil\\Desktop\\"+date+".xlsx";
        File file=new File(s);
        if(!file.exists()){//如果不存在
            throw new RuntimeException("文件不存在:"+s);
        }
        List<List<Object>> rows=ExcelUtils.read(file);
        List<Express> expressList=Lists.newArrayList();
        List<Object> cell;//表头
        if(rows != null && rows.size()>0){
            cell=rows.get(0);
            //线验证文件在不
            for(int k=1; k<rows.size(); k++){
                for(int j=0; j<cell.size(); j++){
                    String key=(String) cell.get(j);
                    String v=(String) rows.get(k).get(j);
                    switch(key){
                        case "img":
                            String fileName="C:\\Users\\Phil\\Desktop\\"+date+"\\IMG_"+date+"_"+v+".jpg";
                            File f=new File(fileName);
                            if(f.exists()){//如果存在
                                String time=date+v;
                                DateTime dateTime=DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(time);
                                System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
                            }else{
                                throw new RuntimeException("文件不存在:"+f.getName());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            for(int i=1; i<rows.size(); i++){
                Express express=new Express();
                for(int j=0; j<cell.size(); j++){
                    String k=(String) cell.get(j);
                    String v=(String) rows.get(i).get(j);
                    switch(k){
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
                            String fileName="C:\\Users\\Phil\\Desktop\\"+date+"\\IMG_"+date+"_"+v+".jpg";
                            File f=new File(fileName);
                            if(f.exists()){//如果存在
                                String time=date+v;
                                DateTime dateTime=DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(time);
                                express.setCreateTime(dateTime.toDate());
                                express.setUrl(QiNiuUtils.upload(f));
                            }else{
                                throw new RuntimeException("文件不存在:"+f.getName());
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
    
    @RequestMapping(value="list", method=RequestMethod.GET)
    public Object list(DTRequest dtRequest, @RequestParam("search[value]") String q) {
        dtRequest.setQ(q);
        return expressService.findByNumberLike(dtRequest);
    }
    
    
    @RequestMapping(value="save", method=RequestMethod.POST)
    public Object save(Express express, @RequestParam("time") String time, @RequestParam(value="file", required=false) MultipartFile multipartFile) {
        File file=null;
        DateTime dateTime=DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(time);
        try{
            if(multipartFile != null){
                String filename=multipartFile.getOriginalFilename();
                file=File.createTempFile(filename, null);
                multipartFile.transferTo(file);
                express.setUrl(QiNiuUtils.upload(file, filename));
            }
            express.setCreateTime(dateTime.toDate());
            expressService.save(express);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(file != null){
                file.deleteOnExit();
            }
        }
        JSONObject jo=new JSONObject();
        jo.put("code", 0);
        jo.put("msg", "成功");
        return jo;
    }
    
    /**
     * 订单价格统计
     *
     * @return
     */
    @RequestMapping(value="charts/price", method=RequestMethod.GET)
    public Object chartsPrice(@RequestParam(defaultValue="7") int days) {
        List<Map<String,Object>> mapList=expressService.chartsPrice(days);
        List<Integer> sum=Lists.newArrayList();
        List<Integer> count=Lists.newArrayList();
        List<String> categories=Lists.newArrayList();
        for(Map<String,Object> map : mapList){
            sum.add(Integer.valueOf(map.get("sum").toString()));
            count.add(Integer.valueOf(map.get("count").toString()));
            categories.add(map.get("create_time").toString());
        }
        JSONObject jo=new JSONObject();
        jo.put("sum", sum);
        jo.put("count", count);
        jo.put("categories", categories);
        return jo;
    }
    
}
