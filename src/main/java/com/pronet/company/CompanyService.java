package com.pronet.company;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("CompanyService")
public class CompanyService {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public CompanyService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;


    public JSONObject updateDetailsAt(CompanyDetails companyDetails){

        JSONObject jsonObject = new JSONObject();

        Table companyProfileTable = dyDB.getTable("CompanyProfile");
        Map<String, String> itemName = new HashMap<String, String>();
        Map<String, Object> itemValue = new HashMap<String, Object>();

            itemName.put("#overview", "overview");

            itemValue.put(":overview", companyDetails.getOverview());
            UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                    "id",          // key attribute name
                    companyDetails.getId(),           // key attribute value
                    "set #overview =:overview", // UpdateExpression
                    itemName,
                    itemValue);

        jsonObject.put("user_name",companyDetails.getUser_name());
        jsonObject.put("id", companyDetails.getId());
        jsonObject.put("logo", companyDetails.getLogo());
        jsonObject.put("url", companyDetails.getUrl());
        jsonObject.put("overview",companyDetails.getOverview());

        return jsonObject;


    }

    public JSONObject getCompanyDetailsAt(String id, String currentID){

        JSONObject json = new JSONObject();
        try {
            CompanyDetails getCompany = mapper.load(CompanyDetails.class, id);
            json.put("page", "C");
            json.put("id", getCompany.getId());
            json.put("name", getCompany.getUser_name());
            if (getCompany.getLogo() == null) {
                json.put("Logo", "/assets/images/companylogo.jpg");
            } else {
                json.put("Logo", getCompany.getLogo());
            }

            if (getCompany.getUrl() == null) {
                json.put("URL", "Company URL");
            } else {
                json.put("URL", getCompany.getUrl());
            }

            if (getCompany.getOverview() == null) {
                json.put("Overview", "Give your company overview");
            } else {
                json.put("Overview", getCompany.getOverview());
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
        return json;

    }
}
