package com.pronet.profile;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.pronet.BadRequestException;
import com.pronet.company.CompanyDetails;
import com.pronet.signup.SignUp;
import com.pronet.signup.SignUpRowMapper;
import com.pronet.userdetails.UserDetails;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ProfileService")
public class ProfileService {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    public JSONObject getProfileAt(int Id) throws EmptyResultDataAccessException {

        JSONObject json= new JSONObject();
        try {

            //fetch the login details for the ID
            String sql1 = "SELECT * FROM user_login WHERE ID =" + Id ;
            List<SignUp> user = jdbcTemplate.query(sql1, new SignUpRowMapper());

            for(SignUp u : user) {
                //select user_details from dynamo
                if (u.getRole().equals("U")) {

                    UserDetails getUser = mapper.load(UserDetails.class, u.getID());
                    json.put("role" , "U");
                    json.put("id" , getUser.getId());
                    json.put("img" , getUser.getImg());
                    json.put("user_name", getUser.getUser_name());
                    json.put("region" , getUser.getRegion());
                    json.put("education", getUser.getEducation());
                    json.put("workex", getUser.getWorkex());
                    json.put("summary", getUser.getSummary());
                    json.put("educationDetails", getUser.getEducationDetails());
                    json.put("certifications", getUser.getCertifications());
                    json.put("skills", getUser.getSkills());
                    json.put("lastseen", getUser.getLastseen());
                }
                else
                {
                    CompanyDetails getCompany = mapper.load(CompanyDetails.class, u.getID());
                    json.put("role","C");
                    json.put("id" , getCompany.getId());
                    json.put("company_name" , getCompany.getUser_name());
                    json.put("logo" , getCompany.getLogo());
                    json.put("url" , getCompany.getUrl());
                    json.put("overview", getCompany.getOverview());
                }
            }
            //return user details to front end

        } catch (EmptyResultDataAccessException e) {
            System.out.println("User does not exists");
            throw new BadRequestException("User does not exists");
        }
        return json;

    }

    public void updateProfileAt(ProfileImgUpdateModel model){


        Table companyProfileTable = dyDB.getTable("CompanyProfile");
        Map<String, String> itemName = new HashMap<String, String>();
        Map<String, Object> itemValue = new HashMap<String, Object>();

            itemName.put("#logo", "logo");
            itemValue.put(":logo", model.getLogo());
            UpdateItemOutcome outcome =  companyProfileTable.updateItem(
                    "id",          // key attribute name
                    model.getId(),           // key attribute value
                    "set #logo=:logo", // UpdateExpression
                    itemName,
                    itemValue);




    }
}
