package com.pronet.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Component("companyController")
public class companyController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonDynamoDBClient dynamoDBClient;

    @RequestMapping(value = "/companyprofile/{id}/{currentID}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONObject getCompanyDetails(@PathVariable("id") String id, @PathVariable("currentID") String currentID) throws Exception {
        JSONObject json = new JSONObject();
        try {
            CompanyProfile getCompany = mapper.load(CompanyProfile.class, id);
            json.put("page", "C");
            json.put("id", getCompany.getId());
            json.put("name", getCompany.getName());
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

    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void companyProfile(@Valid @RequestBody CompanyProfile model, BindingResult result) throws EmptyResultDataAccessException {
        {
            // System.out.println(model);
            // System.out.println(id);
            System.out.println(model.getId());
            String name=model.getName();
            String url =model.getUrl();

            try {
                String  id=model.getId();
                if (!id.isEmpty())
                {
                    System.out.println("insert into CompanyProfile");
                    //insert into dynamo db , userID and name

                    Table companyProfileTable = dyDB.getTable("CompanyProfile");
                    Map<String, String> itemName = new HashMap<String, String>();
                    Map<String, Object> itemValue = new HashMap<String, Object>();

                    if(name!=null &&  url!=null) {
                        itemName.put("#name", "name");

                        itemValue.put(":name", model.getName());
                        itemName.put("#url", "url");
                        itemValue.put(":url", model.getUrl());
                        UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                                "id",          // key attribute name
                                id,           // key attribute value
                                "set #name =:name , #url=:url", // UpdateExpression
                                itemName,
                                itemValue);
                        System.out.println("Updated both(name,url)");
                        System.out.println("done");
                    }
                    else if(name!=null)
                    {
                        itemName.put("#name", "name");
                        itemValue.put(":name", model.getName());
                        UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                                "id",          // key attribute name
                                id,           // key attribute value
                                "set #name =:name ", // UpdateExpression
                                itemName,
                                itemValue);
                        System.out.println("Updated name");

                    }else if(url!=null)
                    {
                        itemName.put("#url", "url");
                        itemValue.put(":url", model.getUrl());
                        UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                                "id",          // key attribute name
                                id,           // key attribute value
                                "set #url =:url ", // UpdateExpression
                                itemName,
                                itemValue);
                        System.out.println("Updated url");

                    }
                    else{
                        System.out.println("Nothing to update");
                    }
                    //Item dyn = new Item()
//                            .withPrimaryKey("id", id)
//                            .withString("logo", "My creds were not working so just to test")
//                            .withString("name", "I used your")
//                            .withString("overview", "creds");
                }

            } catch (EmptyResultDataAccessException e) {

            }
        }
        //return new ResponseEntity<String>("true",HttpStatus.CREATED);
    }
}
