package org.javaboy.mailserver.receiver;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Hr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @ClassName MailReceiver
 * @Author 周某
 * @Date 2020-05-18 11:37
 **/
@Component
public class MailReceiver {

    public static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    // 邮件发送
    @Autowired
    JavaMailSender javaMailSender;
    // 获取邮件发送者邮箱名
    @Autowired
    MailProperties mailProperties;
    // 设置模板实体类
    @Autowired
    TemplateEngine templateEngine;

    // 监听 `changingTao.mail.welcome` 消息队列，如果出现新消息则进行下面的操作
    @RabbitListener(queues = "changingTao.mail.welcome")
    public void handler(Employee employee) {
        logger.info(employee.toString());
        // 收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            // 设置邮件标题
            helper.setSubject("入职欢迎");
            // 设置时间
            helper.setSentDate(new Date());

            // 设置模板内容（赋值）
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJobLevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());

            // 模板渲染：主题，内容
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            // 发送邮件
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("邮件发送失败，原因：" + e.getMessage());
        }
    }
}

























