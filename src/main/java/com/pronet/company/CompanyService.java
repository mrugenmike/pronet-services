package com.pronet.company;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.pronet.exceptions.BadRequestException;
import com.pronet.feeds.FeedsModel;
import com.pronet.search.company.CompanyFields;
import com.pronet.signup.SignUpRowMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("CompanyService")
public class CompanyService {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public CompanyService(JdbcTemplate jdbcTemplate,RedisTemplate<String, String> redisTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    private RedisTemplate<String, String> redisTemplate;

    long score = 0;


    public JSONObject updateDetailsAt(CompanyDetails companyDetails){

        JSONObject jsonObject = new JSONObject();
        String puturl;
        String putoverview;

        CompanyDetails getCompany = mapper.load(CompanyDetails.class,companyDetails.getId());

        Table companyProfileTable = dyDB.getTable("CompanyProfile");
        Map<String, String> itemName = new HashMap<String, String>();
        Map<String, Object> itemValue = new HashMap<String, Object>();

        if(companyDetails.getUrl()==null)
            puturl = getCompany.getUrl();
        else
            puturl = companyDetails.getUrl();

        if(companyDetails.getOverview()==null)
            putoverview = getCompany.getOverview();
        else
            putoverview = companyDetails.getOverview();



            itemName.put("#overview", "overview");
            itemName.put("#url", "url");
            itemValue.put(":overview", putoverview);
            itemValue.put(":url", puturl);
            UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                    "id",          // key attribute name
                    companyDetails.getId(),           // key attribute value
                    "set #overview =:overview,#url=:url", // UpdateExpression
                    itemName,
                    itemValue);

        jsonObject.put("user_name",companyDetails.getUser_name());
        jsonObject.put("id", companyDetails.getId());
        jsonObject.put("logo", companyDetails.getLogo());
        jsonObject.put("url", companyDetails.getUrl());
        jsonObject.put("overview",companyDetails.getOverview());

        //inserting company details into redis for search
        String c_id_redis = companyDetails.getId();
        String tag = companyDetails.getUser_name().toLowerCase().replace(" ", "_");
        final String keyForHash = String.format("company:%s", c_id_redis);
        final Map< String, Object > properties = new HashMap< String, Object >();

        String tmpLogo = "http://pronetnode.elasticbeanstalk.com/assets/images/companylogo.jpg";
        properties.put(CompanyFields.COMPANYID.toString(), c_id_redis);
        properties.put(CompanyFields.COMPANYNAME.toString(),tag);
        properties.put(CompanyFields.COMAPANYLOGO.toString(),tmpLogo);
        properties.put(CompanyFields.COMPANYDESC.toString(),companyDetails.getOverview());


        //query: hgetall jobs:11 / 11 is jobID
        redisTemplate.opsForHash().putAll(keyForHash, properties);

        final String keyForSet = String.format("tags:company:%s", tag);
        score = redisTemplate.opsForZSet().size(keyForSet);
        //populating tags for search
        //ZRANGE tags:jobs:new_position_for_SE 0 1 WITHSCORES
        redisTemplate.opsForZSet().add(keyForSet, companyDetails.getId(), score + 1);

        return jsonObject;


    }

    public JSONObject getCompanyDetailsAt(String id, String currentID){

        JSONObject json = new JSONObject();
        try {
            CompanyDetails getCompany = mapper.load(CompanyDetails.class, id);
            json.put("page", "C");
            json.put("id", getCompany.getId());
            json.put("company_name", getCompany.getUser_name());
            if (getCompany.getLogo() == null) {
           // json.put("logo", "/assets/images/companylogo.jpg");
           //Logo - confirm case
	       json.put("logo", "http://pronetnode.elasticbeanstalk.com/assets/images/companylogo.jpg");
            } else {
                json.put("logo", getCompany.getLogo());
            }

            if (getCompany.getUrl() == null) {
                json.put("url", "Company URL");
            } else {
                json.put("url", getCompany.getUrl());
            }

            if (getCompany.getOverview() == null) {
                json.put("overview", "Give your company overview");
            } else {
                json.put("overview", getCompany.getOverview());
            }
            System.out.println(json);
        }
        catch(Exception e)
        {
            System.out.println("Company not found");
            throw new BadRequestException("Company not found");

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


        List<FeedsModel> feeds;
        try{

            String sql2 = "SELECT * FROM feeds WHERE user_id =" + id ;
            feeds = jdbcTemplate.query(sql2, new SignUpRowMapper());
        }
        catch (Exception e){
            feeds = new ArrayList<FeedsModel>();
        }
        json.put("feeds",feeds);

        return json;

    }
}
