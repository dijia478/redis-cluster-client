package cn.dijia478.redis5.controller;

import cn.dijia478.redis5.bean.RedisDTO;
import cn.dijia478.redis5.redis.JedisDAO;
import cn.dijia478.redis5.util.IdTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 操作redis的控制层，使用jedis操作
 *
 * @author dijia478
 * @date 2019-4-25 10:00
 */
@RestController
@Slf4j
@Api(description = "redis-cluster集群jedis操作的接口")
public class JedisController {

    @Autowired
    private JedisDAO jedisDAO;

    @ApiOperation(value = "redis的set命令（接口负责人：dijia478）")
    @PostMapping("/redis/jedis/set")
    public String set(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/jedis/set], req: {}", logId, redisDTO);
        String set = jedisDAO.set(logId, redisDTO.getKey(), redisDTO.getValue());
        log.info("[logId:{}] resp: {}", logId, set);
        return set;
    }

    @ApiOperation(value = "redis的get命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/get/{key}")
    public String get(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/get/{}]", logId, key);
        String get = jedisDAO.get(logId, key);
        log.info("[logId:{}] resp: {}", logId, get);
        return get;
    }

    @ApiOperation(value = "redis的del命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/jedis/del/{key}")
    public Long del(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/jedis/del/{}]", logId, key);
        Long del = jedisDAO.del(logId, key);
        log.info("[logId:{}] resp: {}", logId, del);
        return del;
    }

    @ApiOperation(value = "redis的hset命令（接口负责人：dijia478）")
    @PostMapping("/redis/jedis/hset")
    public Long hset(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/jedis/hset], requestBody: {}", logId, redisDTO);
        Long hset = jedisDAO.hset(logId, redisDTO.getKey(), redisDTO.getField(), redisDTO.getValue());
        log.info("[logId:{}] resp: {}", logId, hset);
        return hset;
    }

    @ApiOperation(value = "redis的hget命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/hget/{key}/{field}")
    public String hget(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/hget/{}/{}]", logId, key, field);
        String hget = jedisDAO.hget(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hget);
        return hget;
    }

    @ApiOperation(value = "redis的hgetAll命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/hgetAll/{key}")
    public Map<String, String> hgetAll(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/hgetAll/{}]", logId, key);
        Map<String, String> hgetAll = jedisDAO.hgetAll(logId, key);
        log.info("[logId:{}] resp: {}", logId, hgetAll);
        return hgetAll;
    }

    @ApiOperation(value = "redis的hdel命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/jedis/hdel/{key}/{field}")
    public Long hdel(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/jedis/hdel/{}/{}]", logId, key, field);
        Long hdel = jedisDAO.hdel(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hdel);
        return hdel;
    }

}
