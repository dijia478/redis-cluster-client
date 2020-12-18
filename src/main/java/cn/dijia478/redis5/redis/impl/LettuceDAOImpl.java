package cn.dijia478.redis5.redis.impl;

import cn.dijia478.redis5.redis.RedisDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 使用lettuce操作redis的DAO层实现类，只提供少部分常用的，还需要其他命令可以自己加
 *
 * @author dijia478
 * @date 2020-12-15 17:14
 */
@Repository("lettuceDAOImpl")
@Slf4j
public class LettuceDAOImpl implements RedisDAO {

    @Autowired
    private RedisTemplate<String, String> rt;

    @Override
    public void set(String logId, String key, String value) {
        try {
            rt.opsForValue().set(key, value);
            log.debug("[logId:{}] SET redis key: {}, value: {}", logId, key, value);
        } catch (Exception e) {
            log.error("[logId:{}] SET redis key: {}, value: {}", logId, key, value, e);
        }
    }

    @Override
    public String get(String logId, String key) {
        String get = null;
        try {
            get = rt.opsForValue().get(key);
            log.debug("[logId:{}] GET redis key: {}, result: {}", logId, key, get);
        } catch (Exception e) {
            log.error("[logId:{}] GET redis key: {}", logId, key, e);
        }
        return get;
    }

    @Override
    public Boolean del(String logId, String key) {
        Boolean del = null;
        try {
            del = rt.delete(key);
            log.debug("[logId:{}] DEL redis key: {}, result: {}", logId, key, del);
        } catch (Exception e) {
            log.error("[logId:{}] DEL redis key: {}", logId, key, e);
        }
        return del;
    }

    @Override
    public void hset(String logId, String key, String field, String value) {
        try {
            rt.opsForHash().put(key, field, value);
            log.debug("[logId:{}] HSET redis key: {}, field: {}, value: {}", logId, key, field, value);
        } catch (Exception e) {
            log.error("[logId:{}] HSET redis key: {}, field: {}, value: {}", logId, key, field, value, e);
        }
    }

    @Override
    public String hget(String logId, String key, String field) {
        String hget = null;
        try {
            HashOperations<String, String, String> hash = rt.opsForHash();
            hget = hash.get(key, field);
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
            HashOperations<String, String, String> hash = rt.opsForHash();
            hgetAll = hash.entries(key);
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
            hdel = rt.opsForHash().delete(key, field);
            log.debug("[logId:{}] HDEL redis key: {}, field: {}, result: {}", logId, key, field, hdel);
        } catch (Exception e) {
            log.error("[logId:{}] HDEL redis key: {}, field: {}", logId, key, field, e);
        }
        return hdel;
    }

}
