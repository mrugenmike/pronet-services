package com.pronet.signup;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpRowMapper implements RowMapper
{
        public Object mapRow (ResultSet rs,int rowNum)throws SQLException {
            SignUp signUp = new SignUp();
            signUp.setID(rs.getString(1));
            signUp.setUser_name(rs.getString(2));
            signUp.setEmail(rs.getString(3));
            signUp.setPassword(rs.getString(4));
            signUp.setRole(rs.getString(5));
            signUp.setLast_seen(rs.getString(6));
            return signUp;
    }
}
