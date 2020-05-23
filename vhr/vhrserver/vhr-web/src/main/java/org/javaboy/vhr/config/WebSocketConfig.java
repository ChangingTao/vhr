package org.javaboy.vhr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/** 在线聊天功能配置
 * @ClassName WebSocketConfig
 * @Author 周某
 * @Date 2020-05-20 11:03
 **/
@Configuration // 指明配置类
@EnableWebSocketMessageBroker  // 开启消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 注册端点，前端跟服务端连接的点
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 允许使用socketJs方式访问，访问点为/ws/ep，允许跨域 在网页上我们就可以通过这个链接 http://localhost:8080/ws/ep 来和服务器的WebSocket连接
        // 设置允许跨域 setAllowedOrigins
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }
    // 消息代理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置前端订阅代理
        registry.enableSimpleBroker("/queue");
    }
}