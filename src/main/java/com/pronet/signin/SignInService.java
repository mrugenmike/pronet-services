package com.pronet.signin;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.pronet.BadRequestException;
import com.pronet.signup.signUpModel;
import com.pronet.signup.signUpRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.List;
@Component("SignInService")
public class SignInService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    public JSONObject signInUserAt(SignInModel model) throws EmptyResultDataAccessException{

        JSONObject jsonObject = new JSONObject();
        try {

            String sql = "SELECT ID FROM user_login WHERE email ='" + model.getEmail() + "'";
            String id = jdbcTemplate.queryForObject(sql, String.class);

            //fetch the login details for the ID
            String sql1 = "SELECT * FROM user_login WHERE ID =" + id ;
            List<signUpModel> user = jdbcTemplate.query(sql1, new signUpRowMapper());

            for(signUpModel u : user) {
                if (!u.getPassword().equals(model.getPassword()))
                    throw new BadRequestException("Invalid email or password ");

                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp currentTime = new java.sql.Timestamp(calendar.getTime().getTime());

                String updateTime = "UPDATE user_login SET last_login ='" + currentTime +"' WHERE ID = " + id;
                jdbcTemplate.update(updateTime);

                jsonObject.put("page" , u.getRole());
                jsonObject.put("id", u.getID());
                jsonObject.put("last_login",u.getLast_seen());
            }
            return jsonObject;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("User does not exists");
            throw new BadRequestException("User does not exists");
        }
    }
}
