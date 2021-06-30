package com.websocket.config;

import com.websocket.constant.ApplicationConstants;
import com.websocket.constant.MessageType;
import com.websocket.dto.RedisMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yue WeiWei
 * @date 2021/4/29 14:26
 */
@ServerEndpoint(value = "/api/websocket/{userId}")
@Component
public class Websocket implements RedisMsg {
    private final static Logger log = LoggerFactory.getLogger(Websocket.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
     */
    private static Map<String, Session> socketMap = new ConcurrentHashMap<>();

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        RedisSerializer<String> key = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer value = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(key);
        redisTemplate.setHashKeySerializer(key);
        redisTemplate.setValueSerializer(value);
        redisTemplate.setHashValueSerializer(value);
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        log.info("sessionId:{},userId:{},socket连接建立", session.getId(), userId);
        // 存放 session
        socketMap.put(String.valueOf(userId), session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "userId") String userId) {
        log.info("sessionId:{},userId:{},socket关闭", session.getId(), userId);
        if (session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socketMap.remove(String.valueOf(userId));
        //offLine(userId);
    }

    /**
     * 下线用户
     *
     * @param userId
     */
    private void offLine(Long userId) {
//        StringBuilder sb = new StringBuilder(ApplicationConstants.TOKEN_KEY_PREFIX);
//        String tokenKey = sb.append(userId).append(ApplicationConstants.TOKEN_KEY_SUFFIX).toString();
//        log.info("tokenKey offLine:{}",tokenKey);
//        redisTemplate.delete(tokenKey);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息-->{}", message);

    }

    /**
     * @param message
     * @desc 发送普通消息
     */
    public void sendOneMessage(String userId, String message) {
        String msg = userId + "|" + message;
        log.info("sendOneMessage:{}", msg);
        //广播消息到各个订阅者
        redisTemplate.convertAndSend(ApplicationConstants.WEBSOCKET_CHANNEL, msg);
    }

    /**
     * 发送下线消息
     *
     * @param userId
     * @param message
     */
    public void sendOfflineMessage(String userId, String message) {
        //String msg = userId + "|" + MessageType.OFFLINE.getCode() + ":" + message;
        //log.info("sendOneMessage:{}", msg);
        // 广播消息到各个订阅者
        //redisTemplate.convertAndSend(ApplicationConstants.WEBSOCKET_CHANNEL, msg);
    }

    /**
     * 发送进度消息
     *
     * @param userId
     * @param message
     */
    public void sendLoadingMessage(String userId, String message) {
        String msg = userId + "|" + MessageType.LOADING.getCode() + ":" + message;
        log.info("sendOneMessage:{}", msg);
        // 广播消息到各个订阅者
        redisTemplate.convertAndSend(ApplicationConstants.WEBSOCKET_CHANNEL, msg);
    }

    /**
     * 发送信息给指定用户
     *
     * @param userId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String userId, String message) {
        Session session = socketMap.get(userId);
        if (session == null) {
            return false;
        }
        if (!session.isOpen()) {
            return false;
        }
        try {
            log.info("发送消息-->{}", message);
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.info("发送消息失败-->{}", e.getMessage());
        }
        return true;
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.warn("Websocket连接出错-->{}", error);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("webSocket1 :" + new String(message.getBytes(StandardCharsets.UTF_8)));
    }

}
