package cn.dijia478.cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 一个连接redis-cluster集群的java客户端示例
 * 提供了jedis和lettuce两种连接方式，在redis6的cluster集群下测试通过
 * 整合了最新的SpringBoot2.4
 * 并提供了两个小工具的实现：分布式锁，分布式滑动窗口限流
 *
 * @author dijia478
 * @date 2020-12-16 11:13
 */
@SpringBootApplication
public class RedisClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisClusterApplication.class, args);
    }

}
