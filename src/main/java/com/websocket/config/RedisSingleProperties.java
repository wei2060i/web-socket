package com.websocket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Yue WeiWei
 * @date 2021/4/29 18:06
 */
@Setter
@Getter
@Component
@Configuration
public class RedisSingleProperties {
    /**
     * 连接地址
     */
    @Value("${spring.redis.host:#{null}}")
    private String host;

    /**
     * 端口号，默认6379
     */
    @Value("${spring.redis.port:6379}")
    private int port;

    /**
     * 连接超时时间,单位毫秒，默认3秒
     */
    @Value("${spring.redis.timeout:3000}")
    private int timeout;

    /**
     * 连接密码
     */
    @Value("${spring.redis.password:#{null}}")
    private String password;

    /**
     * 连接的DB
     */
    @Value("${spring.redis.database:0}")
    private int database;

    /**
     * 资源次最大连接数，默认8
     */
    @Value("${redis.single.maxTotal:8}")
    private int maxTotal = 8;

    /**
     * 资源池允许最大空闲连接数	，默认8
     */
    @Value("${redis.single.maxIdle:8}")
    private int maxIdle = 8;

    /**
     * 资源池确保最少空闲连接数	,默认0
     */
    @Value("${redis.single.minIdle:0}")
    private int minIdle = 0;

    /**
     * 是否开启jmx监控，可用于监控,默认为true，开启状态
     */
    @Value("${redis.single.jmxEnabled:true}")
    private boolean jmxEnabled;

    /**
     * 当资源池用尽后，调用者是否要等待。只有当为true时，下面的maxWaitMillis才会生效
     * 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
     */
    @Value("${redis.single.blockWhenExhausted:true}")
    private boolean blockWhenExhausted = true;

    /**
     * 当资源池连接用尽后，调用者的最大等待时间（单位毫秒）	-1：表示永不超时
     * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
     */
    @Value("${redis.single.maxWaitMillis:-1}")
    private long maxWaitMillis;

    /**
     * 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
     */
    @Value("${redis.single.minEvictableIdleTimeMillis:1800000}")
    private long minEvictableIdleTimeMillis;

    /**
     * 向资源池借用连接时是否做连接有效性检查(ping)，无效连接会被移除,默认false
     */
    @Value("${redis.single.testOnBorrow:true}")
    private boolean testOnBorrow;

    /**
     * 向资源池归还连接时是否做连接有效性测试(ping)，无效连接会被移除	默认false
     */
    @Value("${redis.single.testOnReturn:false}")
    private boolean testOnReturn;
}