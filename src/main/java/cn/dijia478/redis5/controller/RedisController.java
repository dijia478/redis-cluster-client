package cn.dijia478.redis5.controller;

import cn.dijia478.redis5.bean.RedisDTO;
import cn.dijia478.redis5.redis.RedisDAO;
import cn.dijia478.redis5.util.IdTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作redis的控制层
 *
 * @author dijia478
 * @version 1.0
 * @date 2019-4-25 10:00
 */
@RestController
@Slf4j
@Api(description = "redis5-cluster集群部分操作的接口")
public class RedisController {

    @Autowired
    private RedisDAO redisDAO;

    @ApiOperation(value = "redis的set命令执行n次的耗时（接口负责人：dijia478）")
    @PostMapping("/tss/redis/set")
    public Long set(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/tss/redis/set], requestBody: {}", logId, redisDTO);
        long start = System.currentTimeMillis();
        for (int i = 0; i < redisDTO.getCounts(); i++) {
            redisDAO.set(logId, redisDTO.getKey(), redisDTO.getValue());
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @ApiOperation(value = "redis的get命令执行n次的耗时（接口负责人：dijia478）")
    @GetMapping("/tss/redis/get/{key}/{counts}")
    public Long get(@PathVariable("key") String key, @PathVariable("counts") Integer counts) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/tss/redis/get/{}/{}]", logId, key, counts);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counts; i++) {
            redisDAO.get(logId, key);
        }
        long end = System.currentTimeMillis();
        return end - start;

    }

    @ApiOperation(value = "redis的del命令执行n次的耗时（接口负责人：dijia478）")
    @DeleteMapping("/tss/redis/del/{key}/{counts}")
    public Long del(@PathVariable("key") String key, @PathVariable("counts") Integer counts) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/tss/redis/del/{}/{}]", logId, key, counts);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counts; i++) {
            redisDAO.del(logId, key);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @ApiOperation(value = "redis的hset命令执行n次的耗时（接口负责人：dijia478）")
    @PostMapping("/tss/redis/hset")
    public Long hset(RedisDTO redisDTO) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive POST request [/tss/redis/hset], requestBody: {}", logId, redisDTO);
        long start = System.currentTimeMillis();
        for (int i = 0; i < redisDTO.getCounts(); i++) {
            redisDAO.hset(logId, redisDTO.getKey(), redisDTO.getField(), redisDTO.getValue());
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @ApiOperation(value = "redis的hget命令执行n次的耗时（接口负责人：dijia478）")
    @GetMapping("/tss/redis/hget/{key}/{field}/{counts}")
    public Long hget(@PathVariable("key") String key, @PathVariable("field") String field,
        @PathVariable("counts") Integer counts) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/tss/redis/hget/{}/{}/{}]", logId, key, field, counts);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counts; i++) {
            redisDAO.hget(logId, key, field);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @ApiOperation(value = "redis的hgetAll命令执行n次的耗时（接口负责人：dijia478）")
    @GetMapping("/tss/redis/hgetAll/{key}/{counts}")
    public Long hgetAll(@PathVariable("key") String key, @PathVariable("counts") Integer counts) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive GET request [/tss/redis/hgetAll/{}/{}]", logId, key, counts);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counts; i++) {
            redisDAO.hgetAll(logId, key);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    @ApiOperation(value = "redis的hdel命令执行n次的耗时（接口负责人：dijia478）")
    @DeleteMapping("/tss/redis/hdel/{key}/{field}/{counts}")
    public Long hdel(@PathVariable("key") String key, @PathVariable("field") String field,
        @PathVariable("counts") Integer counts) {
        String logId = IdTool.getId4();
        log.info("[logId:{}] receive DELETE request [/tss/redis/hdel/{}/{}/{}]", logId, key, field, counts);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counts; i++) {
            redisDAO.hdel(logId, key, field);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

}
