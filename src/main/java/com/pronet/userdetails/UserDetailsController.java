package com.pronet.userdetails;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pronet.BadRequestException;
import com.pronet.Follow.Follow;
import com.pronet.userdetails.UserDetails;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by varuna on 4/1/15.
 */

@Controller
@RestController
@Component("UserDetailsController")
public class UserDetailsController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonDynamoDBClient dynamoDBClient;

    @RequestMapping(value = "/userprofile/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void UpdateUserDetails(@PathVariable("id") String id,@Valid @RequestBody UserDetails user, BindingResult result) throws EmptyResultDataAccessException {


        if (result.hasErrors()) {
            throw new BadRequestException("Error in Request Body");
        }

        //UserID : null Name:null URL:null role:CEO Region:null Education: null workex: null Summary:null Ed Details:null Skills:null cerification:null lastseen:null

        //Table table = dyDB.getTable("UserDetails");
        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("id", new AttributeValue().withS(id));

        Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

        if(user.getName()!=null)
            updateItems.put("name", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getName())));

        if(user.getImgURL()!=null)
        updateItems.put("imgURL", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getImgURL())));

        if(user.getRole()!=null)
        updateItems.put("role", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRole())));

        if(user.getRegion()!=null)
        updateItems.put("region", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRegion())));

        if(user.getEducation()!=null)
        updateItems.put("education", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getEducation())));

        if(user.getWorkExperience()!=null)
        updateItems.put("workExperience", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getWorkExperience())));

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


    @RequestMapping(value = "/userprofile/{id}/{currentID}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject getUserDetails(@PathVariable("id") String id,@PathVariable("currentID") String currentID ) throws Exception
    {
        JSONObject json= new JSONObject();

        try {
            UserDetails getUser = mapper.load(UserDetails.class, id);
            System.out.println(getUser);
            json.put("page", "U");
            json.put("id", getUser.getID());
            json.put("name", getUser.getName());

            if (getUser.getImgURL() == null) {
                json.put("img", "/assets/images/sample.jpg");
            } else {
                json.put("img", getUser.getImgURL());
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

            if (getUser.getWorkExperience() == null) {
                json.put("workex", "Work Experience");
            } else {
                json.put("workex", getUser.getWorkExperience());
            }

            if (getUser.getSummary() == null) {
                json.put("summary", "Summary");
            } else {
                json.put("summary", getUser.getSummary());
            }

            if (getUser.getEducationDetails() == null) {
                json.put("EducationDetails", "Your Education Details");
            } else {
                json.put("EducationDetails", getUser.getEducationDetails());
            }

            if (getUser.getCertifications() == null) {
                json.put("Cerifications", "Your certifications");
            } else {
                json.put("Certifications", getUser.getCertifications());
            }

            if (getUser.getSkills() == null) {
                json.put("Skills", " Your skills");
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

        sql = "SELECT count(1) FROM follow WHERE followeeID ='" + id + "' and followeeID ='" + currentID + "'";
        Integer followStatus  = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(followStatus);

        if(followStatus == 0)
            json.put("follow","Follow");
        else
            json.put("follow","UnFollow");
        return json;
    }

}

