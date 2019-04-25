package cn.dijia478.redis5.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis连接池的配置类
 *
 * @author dijia478
 * @version 1.0
 * @date 2019-04-24 11:38
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class JedisConfig {

    private List<String> servers;

    private int maxTotal;

    private int maxIdle;

    private int minIdle;

    private int connectionTimeout;

    private int soTimeout;

    private int maxAttempts;

    private String passWord;

    @Bean
    public JedisCluster getJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        for (String server : servers) {
            String[] split = server.split(":");
            jedisClusterNodes.add(new HostAndPort(split[0], Integer.valueOf(split[1])));
        }

        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(maxTotal);
        jpc.setMaxIdle(maxIdle);
        jpc.setMinIdle(minIdle);

        return new JedisCluster(jedisClusterNodes, connectionTimeout, soTimeout, maxAttempts, passWord, jpc);
    }
}
