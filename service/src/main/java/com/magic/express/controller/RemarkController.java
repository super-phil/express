package com.magic.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.magic.express.model.Remark;
import com.magic.express.service.RemarkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoXiuFei on 2016/12/10.
 */
@RestController
@RequestMapping("remark")
public class RemarkController {
    @Resource
    private RemarkService remarkService;

    /**
     * 删除
     *
     * @param id id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public Object del(@RequestParam("id") String id) {
        remarkService.del(id);
        return null;
    }

    /**
     * 添加
     *
     * @param text text
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(@RequestParam("text") String text) {
        Remark remark = new Remark();
        remark.setText(text);
        remarkService.save(remark);
        return null;
    }


    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list() {
        List<Map<String, Object>> list = remarkService.list();
        if (list == null) {
            list = Lists.newArrayList();
        }
        JSONObject jo = new JSONObject();
        jo.put("data", list);
        return jo;
    }

}
