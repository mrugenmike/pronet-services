package com.pronet.jobs;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by parin on 4/2/15.
 */
public class JobsGetController {

    /**
     * Created by parin on 4/2/15.
     */
    @RestController
    @Component("JobsController")

    public class JobsController {


        @Autowired
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        @Autowired
        DynamoDB dyDB;

        @RequestMapping(value = "/jobs", method = RequestMethod.POST)
        @ResponseBody
        public void getJobPosts(JobsModel model, BindingResult result) {
/*

            String id = model.getId();
            String description = model.getDesc();
            String title = model.getJtitle();
            String sdate = model.getStart_date();
            String exdate = model.getEx_date();
            String region = model.getRegion();
            String status = model.getStatus();
            String skills = model.getSkills();

            Table jobtable = dyDB.getTable("JobPosting");

            System.out.println(description + " " + id + " " + title + " " + sdate + " " + exdate + " " + status + " " + skills + " " + region);

             Item jobItem = new Item()
                    .withPrimaryKey("id", id)
                    .withString("title", title)
                    .withString("description", description)
                    .withString("skills", skills)
                    .withString("status", status)
                    .withString("region", region)
                    .withString("sdate", sdate)
                    .withString("exdate", exdate
                    );

            jobtable.putItem(jobItem);


            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("JobPosting");

            ScanResult result = dyDB.batchGetItem()scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                printItem(item);
            }

            BatchGetItemOutcome outcome = dyDB.batchGetItem(
                    forumTableKeysAndAttributes, threadTableKeysAndAttributes);
            for (String tableName : outcome.getTableItems().keySet()) {
                System.out.println("Items in table " + tableName);
                List<Item> items = outcome.getTableItems().get(tableName);
                for (Item item : items) {
                    System.out.println(item);
                }
            System.out.println(" row inserted.");
        }
 */

        }
    }}