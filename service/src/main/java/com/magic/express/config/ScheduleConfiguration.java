package com.magic.express.config;

import com.magic.express.model.Income;
import com.magic.express.service.EmailService;
import com.magic.express.service.ExpressService;
import com.magic.express.service.IncomeService;
import com.magic.utils.database.DBUtils;
import com.magic.utils.qiniu.QiNiuUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/18 11:19
 *          ScheduleConfiguration 定时任务
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    private static final Logger logger = Logger.getLogger(ScheduleConfiguration.class);
    @Resource
    private EmailService emailService;
    @Resource
    private ExpressService expressService;
    @Resource
    private IncomeService incomeService;

    /**
     * 每天23:59:55备份数据库并上传到七牛
     */
    @Scheduled(cron = "55 59 23 * * ?")
    public void backupAndSendEmail() {
        String backupPath = "/root/phil/";
        File file = new File(backupPath + "backup-" + LocalDate.now().toString("yyyy-MM-dd") + ".sql");
        try {
            String dbName = "express";
            String dbUser = "root";
            String dbPwd = "200810";
            DBUtils.backup(file.getPath(), dbName, dbUser, dbPwd);
            //上传到七牛云上 并发送邮件
            emailService.sendSimple("717815@163.com", "185509965@qq.com", file.getName(), QiNiuUtils.upload(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计收入
     */
    @Scheduled(cron = "55 59 23 * * ?")
    public void insertIncome() {
        Income income = new Income();
        DateTime now = DateTime.now();
        List<Map<String, Object>> maps = expressService.chartByType(now);
        income.setCreateTime(now.toDate());
        income.setUpdateTime(now.toDate());
        if (!maps.isEmpty()) {
            for (Map<String, Object> map : maps) {
                if (!map.isEmpty()) {
                    switch ((String) map.get("type")) {
                        case "x":
                            income.setX(Integer.valueOf(map.get("sum") + ""));
                            break;
                        case "y":
                            income.setY(Integer.valueOf(map.get("sum") + ""));
                            break;
                        case "w":
                            income.setW(Integer.valueOf(map.get("sum") + ""));
                            break;
                        case "d":
                            income.setD(Integer.valueOf(map.get("sum") + ""));
                            break;
                        case "q":
                            income.setQ(Integer.valueOf(map.get("sum") + ""));
                            break;
                        default:
                            System.out.println("无用的");
                            break;
                    }
                }
            }
        }
        incomeService.save(income);
    }
}
