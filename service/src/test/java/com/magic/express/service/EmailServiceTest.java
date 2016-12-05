package com.magic.express.service;


import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/23 14:29
 *          EmailServiceTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {
    @Resource
    private EmailService emailService;
    @Test
    public void t(){
        File file = new File("E:\\backup-"+LocalDate.now().toString("yyyy-MM-dd")+".sql");
        System.out.println(file.getPath());
    }
    
    
    @Test
    public void sendSimpleEmail() throws Exception {
        emailService.sendSimple("717815@163.com", new String[]{"717815@163.com"}, "测试邮件(邮件主题)", "这是邮件内容");
    }
    
    @Test
    public void sendAttachments() throws Exception {
        File file=new File("C:\\Users\\Phil\\Desktop\\俩俩相忘.doc");
        emailService.sendAttachments("717815@163.com", "717815@163.com", "测试邮件(邮件主题)", "这是邮件内容", file);
    }
}