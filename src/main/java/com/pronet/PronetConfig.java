package com.pronet;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = {"classpath:/db-rds.properties","classpath:/db-redis.properties","classpath:/dynamo.properties"},ignoreResourceNotFound = false)
public class PronetConfig {

    //Redis Connection
    @Value("${redis.host}")
    String redisHost;

    @Value("${redis.port}")
    Integer redisPort;

    //Dynamo Connection
    @Value("${accessKey}")
    String accessKey;

    @Value("${secretKey}")
    String secretKey;

    //RDS connection
    @Value("${spring.datasource.url}")
    String rdsDatasource;

    @Value("${spring.datasource.username}")
    String rdsUserName;

    @Value("${spring.datasource.password}")
    String rdsPassword;

    @Value("${spring.datasource.driver-class-name}")
    String rdsDriver;

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

    @Bean
    DynamoDB amazonDynamoDB() {
        AmazonDynamoDBClient amzDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        amzDynamoDB.setRegion(Region.getRegion(Regions.US_WEST_1));
        //amzDynamoDB.setEndpoint("http://localhost:8000");
        final DynamoDB dynDB = new DynamoDB(amzDynamoDB);
        return dynDB;
    }

    @Bean
    DynamoDBMapper amazonDynamoDBMapper() {
        AmazonDynamoDBClient amzDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        amzDynamoDB.setRegion(Region.getRegion(Regions.US_WEST_1));
        //amzDynamoDB.setEndpoint("http://localhost:8000");
        final DynamoDBMapper mapper = new DynamoDBMapper(amzDynamoDB);
        return mapper;
    }

    @Bean
    AmazonDynamoDBClient amazonDynamoDBClient() {
        AmazonDynamoDBClient amzDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        amzDynamoDB.setRegion(Region.getRegion(Regions.US_WEST_1));
        //amzDynamoDB.setEndpoint("http://localhost:8000");
        //final DynamoDB dynDB = new DynamoDB(amzDynamoDB);
        return amzDynamoDB;
    }


/*
    @Bean
    JdbcTemplate JdbcDB() {
        return new JdbcTemplate();
    }
*/

}
