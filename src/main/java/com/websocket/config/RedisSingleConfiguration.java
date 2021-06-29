package com.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * 单机模式redis配置信息
 *
 * @author yww
 * @date 2020-12-23 11:14
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisSingleConfiguration extends CachingConfigurerSupport {

    @Resource
    private RedisSingleProperties redisSingleProperties;

    @Bean
    public JedisPool jedisPool() {
        log.info("JedisPool注入成功！！");
        log.info("host={}", redisSingleProperties.getHost());
        log.info("port={}", redisSingleProperties.getPort() + "");
        log.info("database={}", redisSingleProperties.getDatabase() + "");
        log.info("timeout={}", redisSingleProperties.getTimeout() + "");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisSingleProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisSingleProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisSingleProperties.getMinIdle());
        jedisPoolConfig.setBlockWhenExhausted(redisSingleProperties.isBlockWhenExhausted());
        jedisPoolConfig.setMaxWaitMillis(redisSingleProperties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(redisSingleProperties.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisSingleProperties.isTestOnReturn());
        jedisPoolConfig.setJmxEnabled(redisSingleProperties.isJmxEnabled());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(redisSingleProperties.getMinEvictableIdleTimeMillis());

        jedisPoolConfig.setTestWhileIdle(true);

        return new JedisPool(jedisPoolConfig,
                redisSingleProperties.getHost(),
                redisSingleProperties.getPort(),
                redisSingleProperties.getTimeout(),
                redisSingleProperties.getPassword(),
                redisSingleProperties.getDatabase()
        );
    }
}
