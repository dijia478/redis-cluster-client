package cn.dijia478.redis5.redis.impl;

import cn.dijia478.redis5.redis.JedisDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

/**
 * 操作redis的DAO层实现类，只提供少部分常用的，还需要其他命令可以自己加
 *
 * @author dijia478
 * @date 2019-4-24 17:32
 */
@Repository
@Slf4j
public class JedisDAOImpl implements JedisDAO {

    @Autowired
    private JedisCluster jc;

    @Override
    public String set(String logId, String key, String value) {
        String set = null;
        try {
            set = jc.set(key, value);
            log.debug("[logId:{}] SET redis key: {}, value: {}, result: {}", logId, key, value, set);
        } catch (Exception e) {
            log.error("[logId:{}] SET redis key: {}, value: {}", logId, key, value, e);
        }
        return set;
    }

    @Override
    public String get(String logId, String key) {
        String get = null;
        try {
            get = jc.get(key);
            log.debug("[logId:{}] GET redis key: {}, result: {}", logId, key, get);
        } catch (Exception e) {
            log.error("[logId:{}] GET redis key: {}", logId, key, e);
        }
        return get;
    }

    @Override
    public Long del(String logId, String key) {
        Long del = null;
        try {
            del = jc.del(key);
            log.debug("[logId:{}] DEL redis key: {}, result: {}", logId, key, del);
        } catch (Exception e) {
            log.error("[logId:{}] DEL redis key: {}", logId, key, e);
        }
        return del;
    }

    @Override
    public Long hset(String logId, String key, String field, String value) {
        Long hset = null;
        try {
            hset = jc.hset(key, field, value);
            log.debug("[logId:{}] HSET redis key: {}, field: {}, value: {}, result: {}", logId, key, field, value, hset);
        } catch (Exception e) {
            log.error("[logId:{}] HSET redis key: {}, field: {}, value: {}", logId, key, field, value, e);
        }
        return hset;
    }

    @Override
    public String hget(String logId, String key, String field) {
        String hget = null;
        try {
            hget = jc.hget(key, field);
            log.debug("[logId:{}] HGET redis key: {}, field: {}, result: {}", logId, key, field, hget);
        } catch (Exception e) {
            log.error("[logId:{}] HGET redis key: {}, field: {}", logId, key, field, e);
        }
        return hget;
    }

    @Override
    public Map<String, String> hgetAll(String logId, String key) {
        Map<String, String> hgetAll = null;
        try {
            hgetAll = jc.hgetAll(key);
            log.debug("[logId:{}] HGETALL redis key: {}, result: {}", logId, key, hgetAll);
        } catch (Exception e) {
            log.error("[logId:{}] HGETALL redis key: {}", logId, key, e);
        }
        return hgetAll;
    }

    @Override
    public Long hdel(String logId, String key, String field) {
        Long hdel = null;
        try {
            hdel = jc.hdel(key, field);
            log.debug("[logId:{}] HDEL redis key: {}, field: {}, result: {}", logId, key, field, hdel);
        } catch (Exception e) {
            log.error("[logId:{}] HDEL redis key: {}, field: {}", logId, key, field, e);
        }
        return hdel;
    }

}
