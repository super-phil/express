package com.magic.express.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/18 11:19
 *          ScheduleConfiguration 定时任务
 */
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println(">>>>>>>>> ScheduleConfiguration.scheduler()");
    }
}
