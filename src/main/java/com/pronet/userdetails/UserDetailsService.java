package com.pronet.userdetails;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pronet.exceptions.BadRequestException;
import com.pronet.search.users.UserFields;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    private RedisTemplate<String, String> redisTemplate;

    long score = 0;

    @Autowired
    public UserDetailsService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

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
        String insertTag;

        if(user.getUser_name()==null) {

            UserDetails getCompany = mapper.load(UserDetails.class, id);

            //redis HASH-MAP
            String tag = getCompany.getUser_name().toLowerCase();
            insertTag = tag.toLowerCase().replace(" ", "_");
        }
        else
        {
            insertTag = user.getUser_name().toLowerCase().replace(" ","_");
        }

        String default_image = "http://pronetnode.elasticbeanstalk.com/assets/images/sample.jpg";
        final String keyForHash = String.format( "users:%s", id );
        final Map< String, Object > properties = new HashMap< String, Object >();


        properties.put(UserFields.USERID.toString(), id);
        properties.put(UserFields.NAME.toString(),insertTag);
        if(user.getImg()!=null) {
            properties.put(UserFields.USERLOGO.toString(), user.getImg());
        }
        else{
            properties.put(UserFields.USERLOGO.toString(), default_image);
        }
        if(user.getRegion()!=null)
            properties.put(UserFields.REGION.toString(),user.getRegion());
        else
            properties.put(UserFields.REGION.toString(),"Not Available");



        //query: hgetall jobs:11 / 11 is jobID
        redisTemplate.opsForHash().putAll(keyForHash, properties);

        final String keyForSet1 = String.format("tags:users:%s", insertTag);
        score = redisTemplate.opsForZSet().size(keyForSet1);
        //populating tags for search
        //ZRANGE tags:jobs:new_position_for_SE 0 0 WITHSCORES
        redisTemplate.opsForZSet().add(keyForSet1, id, score + 1);

    }

    public JSONObject getUserDetailsAt(String id,String currentID){
        JSONObject json= new JSONObject();
        System.out.println("here in user profile");
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
                json.put("certifications", "Certifications");
            } else {
                json.put("certifications", getUser.getCertifications());
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
            json.put("follow","Connect");
        else
            json.put("follow","Connected");

        System.out.println(json);
        return json;
    }

}

