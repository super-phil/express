package com.magic.express.service;

import com.magic.express.exception.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2016/11/23 14:07
 *          EmailService 邮件服务
 */
@Service
public class EmailService {
    private static final Logger logger=Logger.getLogger(EmailService.class);
    @Resource
    private JavaMailSender sender;
    
    //
    //    public void sendSimpleEmail() {
    //        SimpleMailMessage message=new SimpleMailMessage();
    //        message.setFrom("717815@163.com");//发送者.
    //        message.setTo("717815@163.com");//接收者.
    //        message.setSubject("测试邮件（邮件主题）");//邮件主题.
    //        message.setText("这是邮件内容");//邮件内容.
    //        sender.send(message);//发送邮件
    //    }
    //
    
    /**
     * 发送简单的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @throws BusinessException
     */
    public void sendSimple(String from, String to, String subject, String text) throws BusinessException {
        sendSimple(from, new String[]{to}, subject, text);
    }
    
    /**
     * 发送简单的邮件
     *
     * @param from    发送者
     * @param to      多个接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @throws BusinessException
     */
    public void sendSimple(String from, String[] to, String subject, String text) throws BusinessException {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try{
            sender.send(message);//发送邮件
            logger.info("Send email success!");
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param file    附件
     * @throws BusinessException
     */
    public void sendAttachments(String from, String to, String subject, String text, File file) throws BusinessException {
        sendAttachments(from, new String[]{to}, subject, text, new File[]{file});
    }
    
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param file    附件
     * @throws BusinessException
     */
    public void sendAttachments(String from, String[] to, String subject, String text, File file) throws BusinessException {
        sendAttachments(from, to, subject, text, new File[]{file});
    }
    
    
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param files   附件
     * @throws BusinessException
     */
    public void sendAttachments(String from, String to, String subject, String text, File[] files) throws BusinessException {
        sendAttachments(from, new String[]{to}, subject, text, files);
    }
    
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param files   附件
     * @throws BusinessException
     */
    public void sendAttachments(String from, String[] to, String subject, String text, File[] files) throws BusinessException {
        try{
            //这个是javax.mail.internet.MimeMessage下的，不要搞错了。
            MimeMessage mimeMessage=sender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, true);
            //基本设置.
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            //org.springframework.core.io.FileSystemResource下的:
            for(File file : files){
                //添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了。
                helper.addAttachment(file.getName(), new FileSystemResource(file));
            }
            sender.send(mimeMessage);
            logger.info("Send email success!");
        }catch(Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
