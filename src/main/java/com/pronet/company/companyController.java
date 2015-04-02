package com.pronet.company;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.pronet.BadRequestException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by varuna on 4/2/15.
 */

@Controller
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

        console.log(""+ id);
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
