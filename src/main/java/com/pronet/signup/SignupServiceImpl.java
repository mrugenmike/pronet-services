package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by neerajakukday on 3/13/15.
 */
@Component("SignupServiceImpl")
public class SignupServiceImpl implements SignupService {

    public SignupServiceImpl(){}

    //DataService service;

    JdbcTemplate jdbcTemplate;
    @Autowired
    public SignupServiceImpl(JdbcTemplate jdbcTemplate){
         this.jdbcTemplate = jdbcTemplate;
    }




    @Override
    public SignUpDetails signupUser(SignUpDetails signUpDetails) {

        System.out.println("-----------------"+signUpDetails.getFname());

        jdbcTemplate.update(
                "INSERT INTO user(userId,fname,lname,email,password) values(?,?,?,?,?)",
                3,signUpDetails.getFname(), signUpDetails.getLname(),signUpDetails.getEmail(),signUpDetails.getPassword());
        return signUpDetails;
    }
}
