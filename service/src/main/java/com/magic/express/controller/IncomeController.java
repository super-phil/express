package com.magic.express.controller;

import com.magic.express.service.ExpressService;
import com.magic.express.service.IncomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ZhaoXiuFei on 2016/12/17.
 */
@RestController
@RequestMapping("income")
public class IncomeController {

    @Resource
    private IncomeService incomeService;
    @Resource
    private ExpressService expressService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list() {
//        DateTime dateTime = new DateTime(2016, 12, 8, 11, 59, 59);
//        ArrayList<Income> list = Lists.newArrayList();
//        for (int i = 0; i < 9; i++) {
//            DateTime now = dateTime.plusDays(i);
//            List<Map<String, Object>> maps = expressService.chartByType(now);
//            Income income = new Income();
//            income.setCreateTime(now.toDate());
//            if (!maps.isEmpty()) {
//                for (Map<String, Object> map : maps) {
//                    if (!map.isEmpty()) {
//                        switch ((String) map.get("type")) {
//                            case "x":
//                                income.setX(Integer.valueOf(map.get("sum") + ""));
//                                break;
//                            case "y":
//                                income.setY(Integer.valueOf(map.get("sum") + ""));
//                                break;
//                            case "w":
//                                income.setW(Integer.valueOf(map.get("sum") + ""));
//                                break;
//                            case "d":
//                                income.setD(Integer.valueOf(map.get("sum") + ""));
//                                break;
//                            case "q":
//                                income.setQ(Integer.valueOf(map.get("sum") + ""));
//                                break;
//                            default:
//                                System.out.println("无用的");
//                                break;
//                        }
//                    }
//                }
//            }
//            list.add(income);
//            incomeService.save(income);
//        }
        return incomeService.list();
    }
}
