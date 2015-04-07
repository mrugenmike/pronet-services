package com.pronet.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("FollowService")
public class FollowService {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public FollowService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public void followAt(String id, Follow follow){
        //System.out.println("In follow");
        //System.out.println(follow.getFollowerId() + follow.getFollowerName() + follow.getFollowerURL()+follow.getFollowerRole() + id);
        String sql = "SELECT count(1) FROM follow WHERE followerID ='" + id + "' and followeeID ='" +  follow.getFollowerId() + "'";
        Integer foll = jdbcTemplate.queryForObject(sql, Integer.class);

        if(foll == 1)
            return;

        jdbcTemplate.execute(
                "INSERT INTO follow(followerID,followeeID,followeeImgURL,followeeName,followeeRole) values('"
                        + id + "','"
                        + follow.getFollowerId() + "','"
                        + follow.getFollowerURL() + "','"
                        + follow.getFollowerName() + "','"
                        + follow.getFollowerRole() + "')");

    }

    public void UnfollowAt(String id, Follow follow){
        System.out.println("In Unfollow");
        String sql = "SELECT count(1) FROM follow WHERE followerID ='" + id + "' and followeeID ='" +  follow.getFollowerId() + "'";
        Integer foll = jdbcTemplate.queryForObject(sql, Integer.class);

        if(foll == 0)
            return;
        jdbcTemplate.execute(" DELETE FROM follow where followerID = '" + id + "' and followeeID = '" + follow.getFollowerId() + "'");
        System.out.println("Deleted successfully!!");

    }

    public List followingAt(String id){

        //List followers = new ArrayList<>();
        //fetch the login details for the ID
        System.out.println(id);
        String sql1 = "SELECT followeeID,followeeName,followeeImgURL,followeeRole FROM follow WHERE followerID = '" + id + "'";
        List followers = jdbcTemplate.queryForList(sql1);
        System.out.println(followers);
        return followers;

    }
}
