package com.websocket;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yue WeiWei
 * @date 2021/5/8 11:40
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public boolean setBit(String buildSignKey, int offset, boolean b) {
        return redisTemplate.opsForValue().setBit(buildSignKey, offset, b);
    }

    public boolean getBit(String buildSignKey, int offset) {
        return redisTemplate.opsForValue().getBit(buildSignKey, offset);
    }

    public Long bitCount(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
    }

    public List<Long> bitfield(String buildSignKey, int limit,int offset) {
       return redisTemplate.execute(new RedisCallback<List<Long>>() {
            @Override
            public List<Long> doInRedis(RedisConnection redisConnection) throws DataAccessException {
               return redisConnection.bitField(buildSignKey.getBytes(),
                       BitFieldSubCommands.create()
                               .get(BitFieldSubCommands.BitFieldType.unsigned(limit))
                               .valueAt(offset)
                        );
            }
        });
    }
}

