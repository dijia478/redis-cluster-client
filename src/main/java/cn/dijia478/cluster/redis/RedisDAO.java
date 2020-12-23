package cn.dijia478.cluster.redis;

import java.util.Map;

/**
 * redis的DAO层
 *
 * @author dijia478
 * @date 2020-12-15 17:13
 */
public interface RedisDAO {

    /**
     * 释放分布式锁时使用的lua脚本，保证原子性
     *
     * if (redis.call('get', KEYS[1]) == ARGV[1])
     * then
     *   return redis.call('del', KEYS[1])
     * else
     *   return 0
     * end
     */
    String RELEASE_LOCK_LUA = "if (redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 滑动窗口限流使用的lua脚本，保证原子性
     *
     * local key = KEYS[1];
     * local index = tonumber(ARGV[1]);
     * local time_window = tonumber(ARGV[2]);
     * local now_time = tonumber(ARGV[3]);
     * local far_time = redis.call('lindex', key, index);
     * if (not far_time)
     * then
     *   redis.call('lpush', key, now_time);
     *   redis.call('pexpire', key, time_window+1000);
     *   return 1;
     * end
     * if (now_time - far_time > time_window)
     * then
     *   redis.call('rpop', key);
     *   redis.call('lpush', key, now_time);
     *   redis.call('pexpire', key, time_window+1000);
     *   return 1;
     * else
     *   return 0;
     * end
     */
    String SLIDE_WINDOW_LUA = "local key = KEYS[1];\n" + "local index = tonumber(ARGV[1]);\n" + "local time_window = tonumber(ARGV[2]);\n" + "local now_time = tonumber(ARGV[3]);\n" + "local far_time = redis.call('lindex', key, index);\n" + "if (not far_time)\n" + "then\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "end\n" + "\n" + "if (now_time - far_time > time_window)\n" + "then\n" + "  redis.call('rpop', key);\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "else\n" + "  return 0;\n" + "end";

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
     * @param logId      日志id
     * @param key        key
     * @param value      value，需要保证全局唯一，用来删除分布式锁时判断身份使用
     * @param expireTime 锁过期时间，毫秒，防止业务崩溃未删除锁，导致死锁
     * @return 是否获取成功锁
     */
    Boolean getDistributedLock(String logId, String key, String value, long expireTime);

    /**
     * 释放分布式锁
     *
     * @param logId 日志id
     * @param key   key
     * @param value value，需要和获取锁时传入的一致
     * @return 是否释放成功锁
     */
    Boolean releaseDistributedLock(String logId, String key, String value);

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过加锁避免并发问题，性能不高。只是为了说明限流逻辑如何实现
     *
     * @param logId      日志id
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    Boolean slideWindow(String logId, String key, int count, long timeWindow);

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过Lua脚本避免并发问题，性能较高。
     *
     * @param logId      日志id
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    Boolean slideWindowLua(String logId, String key, int count, long timeWindow);

}