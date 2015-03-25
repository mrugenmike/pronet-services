package com.pronet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = {"classpath:/db-rds.properties","classpath:/db-redis.properties"},ignoreResourceNotFound = false)
public class PronetConfig {

    @Value("${redis.host}")
    String redisHost;

    @Value("${redis.port}")
    Integer redisPort;

    @Bean
    JedisConnectionFactory getJedisConnectionFactory(){
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(redisPort);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean
    RedisTemplate getRedisTemplate(){
        final RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    StringRedisTemplate getStringRedisTemplate(){
        final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(getJedisConnectionFactory());
        return stringRedisTemplate;
    }

}
