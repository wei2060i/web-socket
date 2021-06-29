package com.websocket.dto;

import org.springframework.stereotype.Component;

/**
 * @author Yue WeiWei
 * @date 2021/5/6 10:39
 */
@Component
public interface RedisMsg {
    /**
     * 接收信息
     * @param message
     */
    void receiveMessage(String message);
}