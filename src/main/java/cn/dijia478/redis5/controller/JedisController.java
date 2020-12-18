package cn.dijia478.redis5.controller;

import cn.dijia478.redis5.bean.RedisDTO;
import cn.dijia478.redis5.redis.RedisDAO;
import cn.dijia478.redis5.util.IdTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("jedisDAOImpl")
    private RedisDAO redisDAO;

    @ApiOperation(value = "redis的set命令（接口负责人：dijia478）")
    @PostMapping("/redis/jedis/set")
    public void set(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/jedis/set], req: {}", logId, redisDTO);
        redisDAO.set(logId, redisDTO.getKey(), redisDTO.getValue());
    }

    @ApiOperation(value = "redis的get命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/get/{key}")
    public String get(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/get/{}]", logId, key);
        String get = redisDAO.get(logId, key);
        log.info("[logId:{}] resp: {}", logId, get);
        return get;
    }

    @ApiOperation(value = "redis的del命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/jedis/del/{key}")
    public Boolean del(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/jedis/del/{}]", logId, key);
        Boolean del = redisDAO.del(logId, key);
        log.info("[logId:{}] resp: {}", logId, del);
        return del;
    }

    @ApiOperation(value = "redis的hset命令（接口负责人：dijia478）")
    @PostMapping("/redis/jedis/hset")
    public void hset(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/jedis/hset], requestBody: {}", logId, redisDTO);
        redisDAO.hset(logId, redisDTO.getKey(), redisDTO.getField(), redisDTO.getValue());
    }

    @ApiOperation(value = "redis的hget命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/hget/{key}/{field}")
    public Object hget(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/hget/{}/{}]", logId, key, field);
        Object hget = redisDAO.hget(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hget);
        return hget;
    }

    @ApiOperation(value = "redis的hgetAll命令（接口负责人：dijia478）")
    @GetMapping("/redis/jedis/hgetAll/{key}")
    public Map<String, String> hgetAll(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/jedis/hgetAll/{}]", logId, key);
        Map<String, String> hgetAll = redisDAO.hgetAll(logId, key);
        log.info("[logId:{}] resp: {}", logId, hgetAll);
        return hgetAll;
    }

    @ApiOperation(value = "redis的hdel命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/jedis/hdel/{key}/{field}")
    public Long hdel(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/jedis/hdel/{}/{}]", logId, key, field);
        Long hdel = redisDAO.hdel(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hdel);
        return hdel;
    }

}
