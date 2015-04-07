package com.pronet.signup;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.pronet.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            Item dyn = new Item()
                    .withPrimaryKey("id", insertedID)
                    .withString("user_name", model.getUser_name());

            table.putItem(dyn);

            //TODO insert into redis
        }
    }
}



