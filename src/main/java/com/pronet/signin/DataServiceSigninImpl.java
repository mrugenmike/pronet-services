package com.pronet.signin;

import com.pronet.BadRequestException;
import com.pronet.signup.SignUpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by neerajakukday on 3/14/15.
 */
@Component("DataServiceSigninImpl")
public class DataServiceSigninImpl implements DataServiceSignin {

    public DataServiceSigninImpl(){};

    JdbcTemplate jdbcTemplate;

    @Autowired
    public DataServiceSigninImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String userLoginDB(SignUpDetails signUpDetails) throws EmptyResultDataAccessException{

        try {

            String validateEmail = "SELECT email FROM user WHERE email ='"
                    + signUpDetails.getEmail() + "'";

            jdbcTemplate.queryForObject(validateEmail, String.class);
        }
        catch(EmptyResultDataAccessException e)
        {

            throw new BadRequestException("Email not found");
        }

        String validatePassword = "SELECT password FROM user WHERE email ='"
                + signUpDetails.getEmail()+"'";
        String password = jdbcTemplate.queryForObject(validatePassword, String.class);
        if(!(password.equals(signUpDetails.getPassword())))
            throw new BadRequestException("Incorrect Password");

        String sql = "SELECT fname FROM user WHERE email ='"
                + signUpDetails.getEmail()+"'"+ " AND password='"+ signUpDetails.getPassword()+ "'";

            return jdbcTemplate.queryForObject(sql, String.class);
        }

    }



