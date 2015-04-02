package com.pronet.signup;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pronet.PronetConfig;
import com.pronet.userdetails.UserDetails;
import javax.validation.Valid;
import com.pronet.BadRequestException;
import org.springframework.jdbc.core.RowMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by varuna on 3/19/15.
 */
@RestController
@Component("signUpController")
public class signUpController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @RequestMapping(value = "/signingUp", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@Valid @RequestBody signUpModel model, BindingResult result) throws EmptyResultDataAccessException {
        {
            if (model.getName() == null || model.getName().trim().equals(""))
                throw new BadRequestException("Require Name");

            if (result.hasErrors()) {
                throw new BadRequestException("Error in Request Body");
            }

            try {
                System.out.println(model.getEmail() + " " + model.getName() + " " + model.getPassword() + " " + model.getRole() + " ");
                String sql = "SELECT ID FROM SignIn WHERE email ='" + model.getEmail() + "'";
                String id = jdbcTemplate.queryForObject(sql, String.class);
                if (!id.isEmpty())
                    throw new BadRequestException("Account is already registered");

            } catch (EmptyResultDataAccessException e) {

                jdbcTemplate.execute(
                        "INSERT INTO SignIn(name,email,password,role) values('"
                                + model.getName() + "','"
                                + model.getEmail()+ "','"
                                + model.getPassword() + "','"
                                + model.getRole()+"')");

                String sql = "SELECT ID FROM SignIn WHERE email = '" + model.getEmail() + "'";
                String insertedID = jdbcTemplate.queryForObject(sql, String.class);

                Table table ;
                if(model.getRole().equals("U"))
                {
                   table = dyDB.getTable("UserDetails");
                }
                else
                {
                   table = dyDB.getTable("CompanyProfile");
                }

                Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
                Item dyn = new Item()
                        .withPrimaryKey("id", insertedID)
                        .withString("name", model.getName());

                table.putItem(dyn);

                //TODO insert into redis
            }
        }
    }
}
