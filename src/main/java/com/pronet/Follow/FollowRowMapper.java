package com.pronet.Follow;

import com.pronet.signup.signUpModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by varuna on 3/30/15.
 */
public class FollowRowMapper implements RowMapper
{
        public Object mapRow (ResultSet rs,int rowNum)throws SQLException {
            Follow follow = new Follow();
            follow.setId(rs.getString(1));
            follow.setFollowerId(rs.getString(2));
            follow.setFollowerURL(rs.getString(3));
            follow.setFollowerName(rs.getString(4));
            follow.setFollowerRole(rs.getString(5));

            System.out.println(follow.getId() +","+ follow.getFollowerId() +","+ follow.getFollowerName() + follow.getFollowerRole() + follow.getFollowerURL());
            return follow;
    }
}
