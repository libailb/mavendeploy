package com.libai.mavendeploy.client;

import com.libai.mavendeploy.config.RedisConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis client
 *
 * @author qiyu
 * @date 2018-12-13 20:09
 */

@Service
@SuppressWarnings("unchecked")
public class BaseRedisClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseRedisClient.class);

    private Gson gson = new Gson();

    /**
     * 写入缓存
     *
     * @param redisTemplate create by RedisConfig
     * @see RedisConfig
     */
    public boolean set(final String key, Object value, RedisTemplate redisTemplate) {
        logger.debug("set redis key:{}, value:{}", key, gson.toJson(value));

        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     */
    public boolean set(final String key, Object value, Long expireTime, RedisTemplate redisTemplate) {
        logger.debug("set redis key:{}, value:{}, expireTime:{}", key, gson.toJson(value), expireTime);

        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     */
    public void remove(RedisTemplate redisTemplate, final String... keys) {
        logger.debug("remove redis keys-values:{}", gson.toJson(keys));

        for (String key : keys) {
            remove(key, redisTemplate);
        }
    }

    /**
     * 批量删除key
     */
    public void removePattern(final String pattern, RedisTemplate redisTemplate) {
        logger.debug("remove redis keys:{}", pattern);

        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     */
    public void remove(final String key, RedisTemplate redisTemplate) {
        logger.debug("remove redis key-value:{}", key);

        if (exists(key, redisTemplate)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key, RedisTemplate redisTemplate) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     */
    public Object get(final String key, RedisTemplate redisTemplate) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(key);
        logger.debug("get redis key:{}, value:{}", key, gson.toJson(value));
        return value;
    }

    /**
     * 哈希 添加
     */
    public void hmSet(String key, Object hashKey, Object value, RedisTemplate redisTemplate) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     */
    public Object hmGet(String key, Object hashKey, RedisTemplate redisTemplate) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(String k, Object v, RedisTemplate redisTemplate) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     */
    public List<Object> lRange(String k, long l, long l1, RedisTemplate redisTemplate) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     */
    public void add(String key, Object value, RedisTemplate redisTemplate) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 计数加
     */
    public Long incr(String key, RedisTemplate redisTemplate) {
        ValueOperations<String, Object> set = redisTemplate.opsForValue();
        return set.increment(key, 1);
    }

    /**
     * 集合获取
     */
    public Set<Object> setMembers(String key, RedisTemplate redisTemplate) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(String key, Object value, double scoure, RedisTemplate redisTemplate) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key, double score, double score1, RedisTemplate redisTemplate) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, score, score1);
    }
}

