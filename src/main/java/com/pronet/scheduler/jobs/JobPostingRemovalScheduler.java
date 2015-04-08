package com.pronet.scheduler.jobs;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.pronet.search.jobs.JobSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JobPostingRemovalScheduler {
Logger logger = LoggerFactory.getLogger(JobPostingRemovalScheduler.class);

    @Autowired
    public void setAmazonDynamoDBClient(AmazonDynamoDBClient dynamoClient,DynamoDB db,StringRedisTemplate redisClient) {
        this.client = dynamoClient;
        this.db = db;
        this.redisClient = redisClient;
    }

    private  AmazonDynamoDBClient client;
    private DynamoDB db;
    private RedisTemplate redisClient;

    public JobPostingRemovalScheduler(){}


    @Scheduled(fixedDelay =120000,initialDelay=1000)
    public void work() {
        final String table = "JobPosting";
        logger.info("Scanning the {} table for expired job postings",table);
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(table);
        final ScanResult scan = client.scan(scanRequest);
        final Date currentDate = Calendar.getInstance().getTime();
        final List<Map<String, AttributeValue>> itemsTobeDeleted = new ArrayList<Map<String, AttributeValue>>();
        for (Map<String, AttributeValue> item : scan.getItems()){
            final AttributeValue ex_date = item.get("ex_date");
            final Date parse;
            try {
                parse = new SimpleDateFormat("YYYY-MM-DD").parse(ex_date.getS());
                if(parse.before(currentDate)){
                    itemsTobeDeleted.add(item);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        final Table jobpostings = db.getTable(table);
        logger.info("Deleting the {} with following Ids: {}", table, itemsTobeDeleted);
        itemsTobeDeleted.forEach(itemId -> {
            jobpostings.deleteItem(new PrimaryKey("jid", itemId.get("jid").getS()));
        });

        itemsTobeDeleted.forEach(item -> {
            final String jobTitle = Arrays.asList(item.get("jtitle").getS().split(" ")).stream().map(t -> t.toLowerCase()).collect(Collectors.joining("_"));
            final String key = JobSearchService.jobTags +":"+jobTitle;
            final String jid = item.get("jid").getS();
            redisClient.opsForZSet().remove(key, jid);
            redisClient.opsForHash().getOperations().delete(JobSearchService.jobsSchema+jid);
        });
    }
}
