package cn.dijia478.redis5.redis;

import java.util.Map;

/**
 * redis的DAO层
 *
 * @author dijia478
 * @date 2020-12-15 17:13
 */
public interface RedisDAO {

    /** 释放分布式锁时使用的lua脚本，保证原子性 */
    String RELEASE_LOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * redis中的set方法
     *
     * @param logId 日志id
     * @param key   key
     * @param value value
     */
    void set(String logId, String key, String value);

    /**
     * redis中的get方法
     *
     * @param logId 日志id
     * @param key   key
     * @return 操作结果
     */
    String get(String logId, String key);

    /**
     * redis中的del方法
     *
     * @param logId 日志id
     * @param key   key
     * @return 操作结果
     */
    Boolean del(String logId, String key);

    /**
     * redis中的hset方法
     *
     * @param logId 日志id
     * @param key   key
     * @param field field
     * @param value value
     */
    void hset(String logId, String key, String field, String value);

    /**
     * redis中的hget方法
     *
     * @param logId 日志id
     * @param key   key
     * @param field field
     * @return 操作结果
     */
    String hget(String logId, String key, String field);

    /**
     * redis中的hgetAll方法
     *
     * @param logId 日志id
     * @param key   key
     * @return 操作结果
     */
    Map<String, String> hgetAll(String logId, String key);

    /**
     * redis中的hdel方法
     *
     * @param logId 日志id
     * @param key   key
     * @param field field
     * @return 操作结果
     */
    Long hdel(String logId, String key, String field);

    /**
     * 获取分布式锁
     *
     * @param logId 日志id
     * @param key key
     * @param value value，需要保证全局唯一，用来删除分布式锁时判断身份使用
     * @param expireTime 锁过期时间，毫秒，防止业务崩溃未删除锁，导致死锁
     * @return 是否获取成功锁
     */
    Boolean getDistributedLock(String logId, String key, String value, long expireTime);

    /**
     * 释放分布式锁
     *
     * @param logId 日志id
     * @param key key
     * @param value value，需要和获取锁时传入的一致
     * @return 是否释放成功锁
     */
    Boolean releaseDistributedLock(String logId, String key, String value);

}