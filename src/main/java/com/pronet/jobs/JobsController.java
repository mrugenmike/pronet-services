package com.pronet.jobs;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.pronet.posts.PostsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Types;


import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.opsworks.model.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.validation.Valid;
import java.sql.Types;

/**
 * Created by parin on 4/2/15.
 */
@RestController
@Component("JobsController")

public class JobsController {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DynamoDB dyDB;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JobsController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.POST)
    public void saveJobPost(@Valid @RequestBody JobsModel model, BindingResult result) {


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
        System.out.println(" row inserted.");
       // redisTemplate.opsForZSet().
    }


    //    j.zadd("math_class",100.0,"martin");
//    j.zadd("math_class",35.5,"milhouse");

//    jobId;
//    positionTitle;
//    companyName;
//    companyLogoUrl;
//     positionLocation;
//
}
