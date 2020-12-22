package cn.dijia478.redis5.redis.impl;

import cn.dijia478.redis5.redis.RedisDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.Map;

/**
 * 使用jedis操作redis的DAO层实现类，只提供少部分常用的，还需要其他命令可以自己加
 *
 * @author dijia478
 * @date 2019-4-24 17:32
 */
@Repository("jedisDAOImpl")
@Slf4j
public class JedisDAOImpl implements RedisDAO {

    @Autowired
    private JedisCluster jc;

    @Override
    public void set(String logId, String key, String value) {
        try {
            String set = jc.set(key, value);
            log.debug("[logId:{}] SET redis key: {}, value: {}, result: {}", logId, key, value, set);
        } catch (Exception e) {
            log.error("[logId:{}] SET redis key: {}, value: {}", logId, key, value, e);
        }
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
    public Boolean del(String logId, String key) {
        Long del = null;
        try {
            del = jc.del(key);
            log.debug("[logId:{}] DEL redis key: {}, result: {}", logId, key, del);
        } catch (Exception e) {
            log.error("[logId:{}] DEL redis key: {}", logId, key, e);
        }
        return del != null && del.intValue() == 1;
    }

    @Override
    public void hset(String logId, String key, String field, String value) {
        try {
            Long hset = jc.hset(key, field, value);
            log.debug("[logId:{}] HSET redis key: {}, field: {}, value: {}, result: {}", logId, key, field, value, hset);
        } catch (Exception e) {
            log.error("[logId:{}] HSET redis key: {}, field: {}, value: {}", logId, key, field, value, e);
        }
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

    @Override
    public Boolean getDistributedLock(String logId, String key, String value, long expireTime) {
        String set = null;
        try {
            set = jc.set(key, value, SetParams.setParams().nx().px(expireTime));
            log.debug("[logId:{}] getLock redis key: {}, value: {}, expireTime: {}, result: {}", logId, key, value, expireTime, set);
        } catch (Exception e) {
            log.error("[logId:{}] getLock redis key: {}, value: {}, expireTime: {}", logId, key, value, expireTime, e);
        }
        return "OK".equals(set);
    }

    @Override
    public Boolean releaseDistributedLock(String logId, String key, String value) {
        Object result = null;
        try {
            result = jc.eval(RELEASE_LOCK_LUA, Collections.singletonList(key), Collections.singletonList(value));
            log.debug("[logId:{}] releaseLock redis key: {}, value: {}, result: {}", logId, key, value, result);
        } catch (Exception e) {
            log.error("[logId:{}] releaseLock redis key: {}, value: {}", logId, key, value, e);
        }
        return Long.valueOf(1L).equals(result);
    }

    @Override
    public synchronized Boolean slideWindow(String logId, String key, int count, long timeWindow) {
        try {
            // 获取当前时间
            long nowTime = System.currentTimeMillis();
            // 获取队列中，达到限流数量的位置，存储的时间戳
            String farTime = jc.lindex(key, count - 1);
            // 如果为空，说明限流队列还没满，则允许通过，并添加当前时间戳到队列开始位置
            if (farTime == null) {
                jc.lpush(key, String.valueOf(nowTime));
                jc.pexpire(key, timeWindow + 1000L);
                return true;
            }

            // 队列已满（达到限制次数），用当前时间戳 减去 最早添加的时间戳
            if (nowTime - Long.parseLong(farTime) > timeWindow) {
                // 若结果大于timeWindow，则说明在timeWindow内，通过的次数小于等于count
                // 允许通过，并删除最早添加的时间戳，将当前时间添加到队列开始位置
                jc.rpop(key);
                jc.lpush(key, String.valueOf(nowTime));
                jc.pexpire(key, timeWindow + 1000L);
                return true;
            }
            // 若结果小于等于timeWindow，则说明在timeWindow内，通过的次数大于count
            // 不允许通过
            return false;
        } catch (Exception e) {
            log.error("[logId:{}]", logId, e);
            return false;
        }
    }

}
