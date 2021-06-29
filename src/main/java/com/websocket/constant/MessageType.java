package com.websocket.constant;

/**
 * @author Yue WeiWei
 * @date 2021/5/21 15:22
 */
public enum MessageType {

    /**
     * 普通消息
     */
    NORMAL("normal"),

    /**
     * 下线消息
     */
    OFFLINE("offline"),

    /**
     * loading进度消息
     */
    LOADING("loading");

    private final String code;

    MessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
