package com.pronet.userdetails;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.pronet.Skills.skills;
import com.pronet.exceptions.BadRequestException;
import com.pronet.search.users.UserFields;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

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


    @Autowired
    MongoTemplate mongotemplate;

    private RedisTemplate<String, String> redisTemplate;

    long score = 0;

    @Autowired
    public UserDetailsService(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }



    //commenting DynamoDB interaction ; changing it to mongoDB interaction
    public void UpdateUserDetailsAt(String id, UserDetails user){

        //Table table = dyDB.getTable("UserDetails");

        Query searchUserQuery = new Query(Criteria.where("_id").is(id));
        UserDetails savedUser = mongotemplate.findOne(searchUserQuery, UserDetails.class,"UserDetails");
        System.out.println(savedUser);

        HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("id", new AttributeValue().withS(id));

        Map<String, AttributeValueUpdate> updateItems = new HashMap<String, AttributeValueUpdate>();

        if(user.getUser_name()!=null) {
            savedUser.setUser_name(user.getUser_name());
            updateItems.put("user_name", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getUser_name())));
        }

        if(user.getImg()!=null) {
            savedUser.setImg(user.getImg());
            updateItems.put("img", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getImg())));
        }

        if(user.getRole()!=null)
        {
            savedUser.setRole(user.getRole());
            updateItems.put("role", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRole())));
        }

        if(user.getRegion()!=null) {
            savedUser.setRegion(user.getRegion());
            updateItems.put("region", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getRegion())));
        }

        if(user.getEducation()!=null)
        {
            savedUser.setEducation(user.getEducation());
            updateItems.put("education", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getEducation())));
        }

        if(user.getWorkex()!=null) {
            savedUser.setWorkex(user.getWorkex());
            updateItems.put("workex", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getWorkex())));
        }

        if(user.getSummary()!=null) {
            savedUser.setSummary(user.getSummary());
            updateItems.put("summary", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getSummary())));
        }

        if(user.getEducationDetails()!=null) {
            savedUser.setImg(user.getEducationDetails());
            updateItems.put("educationDetails", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getEducationDetails())));
        }

        if(user.getSkills()!=null) {
            savedUser.setSkills(user.getSkills());
            updateItems.put("skills", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getSkills())));

            //delete all existing skills for the user
            System.out.println("Delete all existing skills");
            String sql = "SELECT count(1) FROM skills WHERE user_id =" + id + "";
            Integer numberOfSkills = jdbcTemplate.queryForObject(sql, Integer.class);

            if(numberOfSkills > 0) {
                jdbcTemplate.execute(" DELETE FROM skills where user_id = " + id + "");
                jdbcTemplate.execute(" DELETE FROM skillsPref where user_id = " + id + "");
            }

            //get all skills
            HashMap<String,Integer> skillMapping = new HashMap<String,Integer>();
            String sql1 = "SELECT skillID,skillName FROM skillsMapping";
            List<Map<String, Object>> skillsList = jdbcTemplate.queryForList(sql1);

            for(int i = 0 ; i< skillsList.size() ; i++)
            {
                Map<String, Object> skill = skillsList.get(i);
                skillMapping.put((String)skill.get("skillName" ),(Integer) skill.get("skillID"));
            }

            Random rand = new Random();
            //Add all new skills correspoding to user in skills table
            List<String> newSkillsList = Arrays.asList(user.getSkills().split(","));
            for(String skill : newSkillsList)
            {
                //get skill ID
                System.out.println();
                jdbcTemplate.execute(
                        "INSERT INTO skills(user_id,item_id) values("
                                +  Integer.parseInt(id) + ","
                                +  skillMapping.get(skill.trim().toLowerCase())+ ")");

                jdbcTemplate.execute(
                        "INSERT INTO skillsPref(user_id,item_id,preference) values("
                                +  Integer.parseInt(id) + ","
                                +  skillMapping.get(skill.trim().toLowerCase())+  ","
                                +  (rand.nextInt(5)+1) + ")");
            }
        }

        if(user.getCertifications()!=null){
            savedUser.setCertifications(user.getCertifications());
            updateItems.put("certifications", new AttributeValueUpdate().withAction(AttributeAction.PUT).withValue(new AttributeValue().withS(user.getCertifications())));
        }

        ReturnValue returnValues = ReturnValue.ALL_NEW;

        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName("UserDetails").withKey(key)
                .withAttributeUpdates(updateItems)
                .withReturnValues(returnValues);

        UpdateItemResult res = dynamoDBClient.updateItem(updateItemRequest);

        //save to mongodb
        mongotemplate.save(savedUser, "UserDetails");

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

        UserDetails getUser = mapper.load(UserDetails.class, id);
        String default_image = "http://pronetnode.elasticbeanstalk.com/assets/images/sample.jpg";
        final String keyForHash = String.format( "users:%s", id );
        final Map< String, Object > properties = new HashMap< String, Object >();


        properties.put(UserFields.USERID.toString(), id);
        properties.put(UserFields.NAME.toString(),getUser.getUser_name());
        properties.put(UserFields.USERLOGO.toString(), getUser.getImg());
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
        //System.out.println("here in user profile");
        try {
            //UserDetails getUser = mapper.load(UserDetails.class, id);
            // changed from dynamo to mongodb
            Query searchUserQuery = new Query(Criteria.where("_id").is(id));
            UserDetails getUser = mongotemplate.findOne(searchUserQuery, UserDetails.class,"UserDetails");
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

