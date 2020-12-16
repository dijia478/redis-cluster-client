package cn.dijia478.redis5.controller;

import cn.dijia478.redis5.bean.RedisDTO;
import cn.dijia478.redis5.redis.LettuceDAO;
import cn.dijia478.redis5.util.IdTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 操作redis的控制层，使用lettuce操作
 *
 * @author dijia478
 * @date 2020-12-16 11:26
 */
@RestController
@Slf4j
@Api(description = "redis-cluster集群lettuce操作的接口")
public class LettuceController {

    @Autowired
    private LettuceDAO lettuceDAO;

    @ApiOperation(value = "redis的set命令（接口负责人：dijia478）")
    @PostMapping("/redis/lettuce/set")
    public void set(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/lettuce/set], req: {}", logId, redisDTO);
        lettuceDAO.set(logId, redisDTO.getKey(), redisDTO.getValue());
    }

    @ApiOperation(value = "redis的get命令（接口负责人：dijia478）")
    @GetMapping("/redis/lettuce/get/{key}")
    public String get(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/lettuce/get/{}]", logId, key);
        String get = lettuceDAO.get(logId, key);
        log.info("[logId:{}] resp: {}", logId, get);
        return get;
    }

    @ApiOperation(value = "redis的del命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/lettuce/del/{key}")
    public Boolean del(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/lettuce/del/{}]", logId, key);
        Boolean del = lettuceDAO.del(logId, key);
        log.info("[logId:{}] resp: {}", logId, del);
        return del;
    }

    @ApiOperation(value = "redis的hset命令（接口负责人：dijia478）")
    @PostMapping("/redis/lettuce/hset")
    public void hset(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/redis/lettuce/hset], requestBody: {}", logId, redisDTO);
        lettuceDAO.hset(logId, redisDTO.getKey(), redisDTO.getField(), redisDTO.getValue());
    }

    @ApiOperation(value = "redis的hget命令（接口负责人：dijia478）")
    @GetMapping("/redis/lettuce/hget/{key}/{field}")
    public Object hget(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/lettuce/hget/{}/{}]", logId, key, field);
        Object hget = lettuceDAO.hget(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hget);
        return hget;
    }

    @ApiOperation(value = "redis的hgetAll命令（接口负责人：dijia478）")
    @GetMapping("/redis/lettuce/hgetAll/{key}")
    public Map<Object, Object> hgetAll(@PathVariable("key") String key) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/redis/lettuce/hgetAll/{}]", logId, key);
        Map<Object, Object> hgetAll = lettuceDAO.hgetAll(logId, key);
        log.info("[logId:{}] resp: {}", logId, hgetAll);
        return hgetAll;
    }

    @ApiOperation(value = "redis的hdel命令（接口负责人：dijia478）")
    @DeleteMapping("/redis/lettuce/hdel/{key}/{field}")
    public Long hdel(@PathVariable("key") String key, @PathVariable("field") String field) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/redis/lettuce/hdel/{}/{}]", logId, key, field);
        Long hdel = lettuceDAO.hdel(logId, key, field);
        log.info("[logId:{}] resp: {}", logId, hdel);
        return hdel;
    }

}
