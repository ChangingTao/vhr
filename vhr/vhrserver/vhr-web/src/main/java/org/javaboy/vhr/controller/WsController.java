package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.ChatMsg;
import org.javaboy.vhr.model.Hr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;

/**
 * 消息处理类
 *
 * @ClassName WsController
 * @Author 周某
 * @Date 2020-05-20 11:07
 **/
@Controller
public class WsController {

    public Logger logger = LoggerFactory.getLogger(WsController.class);

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        Hr hr = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setFromNickName(hr.getName());
        chatMsg.setDate(new Date());
        logger.info("发送消息给指定用户");
        // simpMessagingTemplate.convertAndSendToUser： 将消息发送给谁？、前端队列监听、发送的消息对象
        // 这里前端队列默认需要在前面添加 /user，所以前端创建的完整队列为 /user/queue/chat
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }
}