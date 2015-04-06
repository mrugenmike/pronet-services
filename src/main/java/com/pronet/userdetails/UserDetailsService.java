package com.pronet.userdetails;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("UserDetailsService")
public class UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonDynamoDBClient dynamoDBClient;

    public void UpdateUserDetailsAt(String id, UserDetails user){
        //UserID : null Name:null URL:null role:CEO Region:null Education: null workex: null Summary:null Ed Details:null Skills:null cerification:null lastseen:null

        //Table table = dyDB.getTable("UserDetails");
        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("id", new AttributeValue().withS(id));

        Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

        if(user.getUser_name()!=null)
            updateItems.put("user_name", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getUser_name())));

        if(user.getImg()!=null)
            updateItems.put("img", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getImg())));

        if(user.getRole()!=null)
            updateItems.put("role", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRole())));

        if(user.getRegion()!=null)
            updateItems.put("region", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRegion())));

        if(user.getEducation()!=null)
            updateItems.put("education", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getEducation())));

        if(user.getWorkex()!=null)
            updateItems.put("workex", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getWorkex())));

        if(user.getSummary()!=null)
            updateItems.put("summary", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getSummary())));

        if(user.getEducationDetails()!=null)
            updateItems.put("educationDetails", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getEducationDetails())));

        if(user.getSkills()!=null)
            updateItems.put("skills", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getSkills())));

        if(user.getCertifications()!=null)
            updateItems.put("certifications", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getCertifications())));

        ReturnValue returnValues = ReturnValue.ALL_NEW;

        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("UserDetails").withKey(key)
                .withAttributeUpdates(updateItems)
                .withReturnValues(returnValues);

        UpdateItemResult res = dynamoDBClient.updateItem(updateItemRequest);

        //TODO insert into redis - user ID , name , Image

    }

    public JSONObject getUserDetailsAt(String id,String currentID){
        JSONObject json= new JSONObject();

        try {
            UserDetails getUser = mapper.load(UserDetails.class, id);
            System.out.println(getUser);
            json.put("page", "U");
            json.put("id", getUser.getId());
            json.put("name", getUser.getUser_name());

            if (getUser.getImg() == null) {
                json.put("img", "/assets/images/sample.jpg");
            } else {
                json.put("img", getUser.getImg());
            }

            if (getUser.getRole() == null) {
                json.put("userrole", "Role");
            } else {
                json.put("userrole", getUser.getRole());
            }

            if (getUser.getRegion() == null) {
                json.put("region", "Region");
            } else {
                json.put("region", getUser.getRegion());
            }

            if (getUser.getEducation() == null) {
                json.put("education", "Education");
            } else {
                json.put("education", getUser.getEducation());
            }

            if (getUser.getWorkex() == null) {
                json.put("workex", "Work Experience");
            } else {
                json.put("workex", getUser.getWorkex());
            }

            if (getUser.getSummary() == null) {
                json.put("summary", "Summary");
            } else {
                json.put("summary", getUser.getSummary());
            }

            if (getUser.getEducationDetails() == null) {
                json.put("EducationDetails", "Education Details");
            } else {
                json.put("EducationDetails", getUser.getEducationDetails());
            }

            if (getUser.getCertifications() == null) {
                json.put("Cerifications", "Certifications");
            } else {
                json.put("Certifications", getUser.getCertifications());
            }

            if (getUser.getSkills() == null) {
                json.put("Skills", "Skills");
            } else {
                json.put("Skills", getUser.getSkills());
            }

            if (getUser.getLastseen() == null) {
                json.put("LastSeen", "");
            } else {
                json.put("LastSeen", getUser.getLastseen());
            }

        }catch(Exception e)
        {
            System.out.println("User not found");
            throw new BadRequestException("User not found");
        }

        String sql = "SELECT count(1) FROM follow WHERE followeeID ='" + id + "'";
        Integer followerCount  = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(followerCount);
        json.put("followerCount",followerCount);

        sql = "SELECT count(1) FROM follow WHERE followeeID ='" + id + "' and followerID ='" + currentID + "'";
        Integer followStatus  = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(followStatus);

        if(followStatus == 0)
            json.put("follow","Follow");
        else
            json.put("follow","UnFollow");
        return json;
    }

}

