package com.pronet.signup;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by varuna on 3/30/15.
 */
public class signUpRowMapper implements RowMapper
{
        public Object mapRow (ResultSet rs,int rowNum)throws SQLException {
            signUpModel signUp = new signUpModel();
            signUp.setID(rs.getString(1));
            signUp.setName(rs.getString(2));
            signUp.setEmail(rs.getString(3));
            signUp.setPassword(rs.getString(4));
            signUp.setRole(rs.getString(5));

            System.out.println(signUp.getRole() +","+ signUp.getID() +","+ signUp.getEmail() + signUp.getName() + signUp.getPassword());
            return signUp;
    }
}
