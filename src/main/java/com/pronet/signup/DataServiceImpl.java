package com.pronet.signup;

import com.pronet.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by neerajakukday on 3/14/15.
 */
@Component("DataServiceImpl")
public class DataServiceImpl implements DataService {

    public DataServiceImpl(){}

    JdbcTemplate jdbcTemplate;

    @Autowired
    public DataServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SignUpDetails registerUserDB(SignUpDetails signUpDetails) throws EmptyResultDataAccessException{

        try {

            String sql = "SELECT fname FROM user WHERE email ='"
                    + signUpDetails.getEmail() + "'" + " AND password='" + signUpDetails.getPassword() + "'";
            String fname = jdbcTemplate.queryForObject(sql, String.class);
            if(!fname.equals(""))
                throw new BadRequestException("User Exists");
        }catch(EmptyResultDataAccessException e) {
            Random random = new Random();

            jdbcTemplate.update(
                    "INSERT INTO user(userId,fname,lname,email,password) values(?,?,?,?,?)",
                    random.nextInt(10), signUpDetails.getFname(), signUpDetails.getLname(), signUpDetails.getEmail(), signUpDetails.getPassword());
        }
        return signUpDetails;
    }
}
