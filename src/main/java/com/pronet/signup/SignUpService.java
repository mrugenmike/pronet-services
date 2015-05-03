package com.pronet.signup;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.pronet.exceptions.BadRequestException;
import com.pronet.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("SignUpService")
public class SignUpService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    MongoTemplate mongotemplate;

    public void signUpUser(SignUp model) throws EmptyResultDataAccessException {

        try {
            String sql = "SELECT ID FROM user_login WHERE email ='" + model.getEmail() + "'";
            String id = jdbcTemplate.queryForObject(sql, String.class);
            if (!id.isEmpty())
                throw new BadRequestException("Account is already registered");

        } catch (EmptyResultDataAccessException e) {

            jdbcTemplate.execute(
                    "INSERT INTO user_login(name,email,password,role) values('"
                            + model.getUser_name() + "','"
                            + model.getEmail() + "','"
                            + model.getPassword() + "','"
                            + model.getRole() + "')");

            String sql = "SELECT ID FROM user_login WHERE email = '" + model.getEmail() + "'";
            String insertedID = jdbcTemplate.queryForObject(sql, String.class);

            Table table;

            if (model.getRole().equals("U"))
                table = dyDB.getTable("UserDetails");
            else
                table = dyDB.getTable("CompanyProfile");

            Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
            Item dyn = new Item();
            if(model.getRole().equals("U"))
            {
                dyn.withPrimaryKey("id", insertedID)
                        .withString("img", "http://pronetnode.elasticbeanstalk.com/assets/images/sample.jpg")
                        .withString("user_name",model.getUser_name());

            }
            else
            {
                dyn.withPrimaryKey("id", insertedID)
                        .withString("user_name", model.getUser_name())
                        .withString("url", "http://")
                        .withString("logo", "http://pronetnode.elasticbeanstalk.com/assets/images/companylogo.jpg")
                        .withString("overview", "overview")
                        .withString("role", model.getRole());
            }
            table.putItem(dyn);

            //insert user into mongodb
            UserDetails user = new UserDetails();
            user.setId(insertedID);
            user.setUser_name(model.getUser_name());
            user.setImg("/assets/images/sample.jpg");
            //moderatorData.put(mod.getJobid(), mod);
            mongotemplate.save(user,"UserDetails");

        }
    }
}



