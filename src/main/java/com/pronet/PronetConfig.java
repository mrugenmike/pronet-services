package com.pronet;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@PropertySource(value = {"classpath:/rds.properties","classpath:/db-redis.properties","classpath:/dynamo.properties", "classpath:/mongodb.properties"},ignoreResourceNotFound = false)
public class PronetConfig {

    //Redis Connection
    @Value("${redis.host}")
    String redisHost;

    @Value("${redis.port}")
    Integer redisPort;

    //mongoDB connections
    @Value("${mongo.uri}")
    String mongoURI;


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
        final DynamoDB dynDB = new DynamoDB(amzDynamoDB);
        return dynDB;
    }

    @Bean
    DynamoDBMapper amazonDynamoDBMapper() {
        AmazonDynamoDBClient amzDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        amzDynamoDB.setRegion(Region.getRegion(Regions.US_WEST_1));
        final DynamoDBMapper mapper = new DynamoDBMapper(amzDynamoDB);
        return mapper;
    }

    @Bean
    AmazonDynamoDBClient amazonDynamoDBClient() {
        AmazonDynamoDBClient amzDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        amzDynamoDB.setRegion(Region.getRegion(Regions.US_WEST_1));
        return amzDynamoDB;
    }

    @Bean
    JdbcTemplate JdbcDB() {
        final BasicDataSource basicDataSource = new BasicDataSource();
        System.out.println(rdsDriver);
        System.out.println(rdsDatasource);
        System.out.println(rdsUserName);
        System.out.println(rdsPassword);

        basicDataSource.setDriverClassName(rdsDriver);
        basicDataSource.setUrl(rdsDatasource);
        basicDataSource.setUsername(rdsUserName);
        basicDataSource.setPassword(rdsPassword);
        return new JdbcTemplate(basicDataSource);
    }

    @Bean
    MysqlDataSource recommendation() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("pronet.ccjszyy4euqe.us-west-1.rds.amazonaws.com");
        dataSource.setPort(3306);
        dataSource.setUser("pronet");
        dataSource.setPassword("pronetapp");
        dataSource.setDatabaseName("pronet");
        return dataSource;
    }

    @Bean
    public  MongoTemplate mongoTemplate() throws Exception {
        MongoClientURI uri = new MongoClientURI(mongoURI);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(uri), "pronet");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
