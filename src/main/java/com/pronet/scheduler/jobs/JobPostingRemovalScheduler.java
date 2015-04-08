package com.pronet.scheduler.jobs;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobPostingRemovalScheduler {


    @Autowired
    public void setAmazonDynamoDBClient(AmazonDynamoDBClient dynamoClient) {
        this.client = dynamoClient;
    }

    private  AmazonDynamoDBClient client;
    public JobPostingRemovalScheduler(){}


    @Scheduled(fixedDelay =120000,initialDelay=1000)
    public void work(){
        final String table = "JobPosting";
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(table);
        final ScanResult scan = client.scan(scanRequest);

        for (Map<String, AttributeValue> item : scan.getItems()){
            System.out.println(item.get("ex_date"));
        }


    }


}
