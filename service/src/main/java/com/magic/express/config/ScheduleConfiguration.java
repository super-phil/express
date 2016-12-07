package com.magic.express.config;

import com.magic.express.service.EmailService;
import com.magic.utils.database.DBUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/18 11:19
 *          ScheduleConfiguration 定时任务
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    private static final Logger logger=Logger.getLogger(ScheduleConfiguration.class);
    @Resource
    private EmailService emailService;
    
    /**
     * 每天8点备份数据库并发送邮件
     */
    @Scheduled(cron="0 0 20 * * ?")
    public void backupAndSendEmail() {
        String backupPath="/root/phil/";
        File file=new File(backupPath+"backup-"+LocalDate.now().toString("yyyy-MM-dd")+".jpg");
        try{
            String dbName="express";
            String dbUser="root";
            String dbPwd="1Q2w3e4r";
            DBUtils.backup(file.getPath(), dbName, dbUser, dbPwd);
            String email="717815@163.com";
            emailService.sendAttachments(email, email, "express数据库备份", "数据库备份请查看附件!", file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}