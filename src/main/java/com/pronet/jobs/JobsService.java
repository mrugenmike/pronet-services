package com.pronet.jobs;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("JobsService")
public class JobsService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JobsService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void saveJobPostAt(JobsModel model){

        String id = model.getId();
        String description = model.getDesc();
        String title = model.getJtitle();
        String sdate = model.getStart_date();
        String exdate = model.getEx_date();
        String region = model.getJob_region();
        String status = model.getStatus();
        String skills = model.getSkills();

        Table jobtable = dyDB.getTable("JobPosting");

        String jid = 1 + ((int)(Math.random()*2000))+"";
        model.setJid(jid);

        Item jobItem = new Item()
                .withPrimaryKey("jid", jid)
                .withString("id", id)
                .withString("title", title)
                .withString("description", description)
                .withString("skills", skills)
                .withString("status", status)
                .withString("job_region", region)
                .withString("sdate", sdate)
                .withString("exdate", exdate
                );

        jobtable.putItem(jobItem);

        //Fetch Company Logo URL and Company Name from DynamoDB

        Table company_table = dyDB.getTable("CompanyProfile");
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("id", model.getId())
                .withProjectionExpression("id,logo,user_name")
                .withConsistentRead(true);
        Item item = company_table.getItem(spec);
        //System.out.println(item.toJSONPretty());

        //redis HASH-MAP
        final String keyForHash = String.format( "jobs:%s", model.getJid() );
        final Map< String, Object > properties = new HashMap< String, Object >();

        properties.put("companyId", model.getId() );
        properties.put("positionTitle", model.getJtitle());
        properties.put("companyName", item.get("user_name"));//how will i get company name???
        properties.put("companyLogoUrl", item.get("logo"));//from s3 get url
        properties.put("positionLocation", model.getJob_region());
        properties.put("status",model.getStatus());

        //query: hgetall jobs:11 / 11 is jobID
        redisTemplate.opsForHash().putAll(keyForHash, properties);

        String tag = model.getJtitle().replace(" ","_");

        final String keyForSet = String.format( "tags:jobs:%s", tag );
        System.out.println(keyForSet);
        //populating tags for search
        //ZRANGE tags:jobs:new_position_for_SE 0 1 WITHSCORES
        redisTemplate.opsForZSet().add(keyForSet, jid, 0);
    }

    public JSONObject getJobDetailsAt(String jid){



        JSONObject jsonObject = new JSONObject();

        Table company_table1 = dyDB.getTable("JobPosting");
        GetItemSpec spec1 = new GetItemSpec()
                .withPrimaryKey("jid", jid)
                .withProjectionExpression("id,description,job_region,skills,title")
                .withConsistentRead(true);
        Item item1 = company_table1.getItem(spec1);


        Table company_table = dyDB.getTable("CompanyProfile");
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("id", "2")
                .withProjectionExpression("id,logo,user_name")
                .withConsistentRead(true);
        Item item = company_table.getItem(spec);

        jsonObject.put("jid",jid);
        jsonObject.put("c_id",item.get("id"));//company ID
        jsonObject.put("title",item1.get("title"));
        jsonObject.put("description",item1.get("description"));
        jsonObject.put("user_name",item.get("user_name"));
        jsonObject.put("logo",item.get("logo"));
        jsonObject.put("skills",item1.get("skills"));
        jsonObject.put("job_region",item1.get("job_region"));

        return jsonObject;





    }
}
