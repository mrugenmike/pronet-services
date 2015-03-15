package com.pronet.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by neerajakukday on 3/14/15.
 */
@Component("DataServiceImpl")
public class DataServiceImpl implements DataService{

    public DataServiceImpl(){}

    JdbcTemplate jdbcTemplate;

    @Autowired
    public DataServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SignUpDetails registerUserDB(SignUpDetails signUpDetails){
        jdbcTemplate.update(
                "INSERT INTO user(userId,fname,lname,email,password) values(?,?,?,?,?)",
                1,signUpDetails.getFname(), signUpDetails.getLname(),signUpDetails.getEmail(),signUpDetails.getPassword());
        return signUpDetails;
    }
}
