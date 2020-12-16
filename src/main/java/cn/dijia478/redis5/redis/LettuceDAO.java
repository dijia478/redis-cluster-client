package cn.dijia478.redis5.redis;

import java.util.Map;

/**
 * 使用lettuce操作redis的DAO层
 *
 * @author dijia478
 * @date 2020-12-15 17:13
 */
public interface LettuceDAO {

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
    Object hget(String logId, String key, String field);

    /**
     * redis中的hgetAll方法
     *
     * @param logId 日志id
     * @param key   key
     * @return 操作结果
     */
    Map<Object, Object> hgetAll(String logId, String key);

    /**
     * redis中的hdel方法
     *
     * @param logId 日志id
     * @param key   key
     * @param field field
     * @return 操作结果
     */
    Long hdel(String logId, String key, String field);

}