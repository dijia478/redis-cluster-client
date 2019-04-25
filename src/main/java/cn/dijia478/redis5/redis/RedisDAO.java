package cn.dijia478.redis5.redis;

import java.util.Map;

/**
 * 操作redis的DAO层
 *
 * @author dijia478
 * @version 1.0
 * @date 2019-4-24 17:32
 */
public interface RedisDAO {

    /**
     * redis中的set方法
     *
     * @param logId 日志id
     * @param key key
     * @param value value
     * @return 操作结果
     */
    String set(String logId, String key, String value);

    /**
     * redis中的get方法
     *
     * @param logId 日志id
     * @param key key
     * @return 操作结果
     */
    String get(String logId, String key);

    /**
     * redis中的del方法
     *
     * @param logId 日志id
     * @param key key
     * @return 操作结果
     */
    Long del(String logId, String key);

    /**
     * redis中的hset方法
     *
     * @param logId 日志id
     * @param key key
     * @param field field
     * @param value value
     * @return 操作结果
     */
    Long hset(String logId, String key, String field, String value);

    /**
     * redis中的hget方法
     *
     * @param logId 日志id
     * @param key key
     * @param field field
     * @return 操作结果
     */
    String hget(String logId, String key, String field);

    /**
     * redis中的hgetAll方法
     *
     * @param logId 日志id
     * @param key key
     * @return 操作结果
     */
    Map<String, String> hgetAll(String logId, String key);

    /**
     * redis中的hdel方法
     *
     * @param logId 日志id
     * @param key key
     * @param field field
     * @return 操作结果
     */
    Long hdel(String logId, String key, String field);

}
