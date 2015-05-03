package com.pronet.follow;

import org.json.simple.JSONObject;
import com.pronet.follow.Follow;
import com.pronet.recommendation.RecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.List;

@Component("FollowService")
public class FollowService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    public FollowService(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
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

        if(follow.getFollowerRole().equals("U"))
        {
            jdbcTemplate.execute(
                    "INSERT INTO connections(user_id,item_id) values("
                            + id + ","
                            + follow.getFollowerId() + ")");

            jdbcTemplate.execute(
                    "INSERT INTO connections(user_id,item_id) values('"
                            +  follow.getFollowerId() + "','"
                            +  id + "')");
        }
    }

    public void UnfollowAt(String id, Follow follow){
        System.out.println("In Unfollow");
        String sql = "SELECT count(1) FROM follow WHERE followerID ='" + id + "' and followeeID ='" +  follow.getFollowerId() + "'";
        Integer foll = jdbcTemplate.queryForObject(sql, Integer.class);

        if(foll == 0)
            return;
        jdbcTemplate.execute(" DELETE FROM follow where followerID = '" + id + "' and followeeID = '" + follow.getFollowerId() + "'");

        if(follow.getFollowerRole().equals("U")) {
            jdbcTemplate.execute(
                    " DELETE FROM connections where user_id = " + id + " and item_id =  "+ follow.getFollowerId() + "");

            jdbcTemplate.execute(
                    " DELETE FROM connections where user_id = " +  follow.getFollowerId() + " and item_id =  "+ id + "");

        }

        System.out.println("Deleted successfully!!");
    }

    public List followingAt(String id) throws TasteException, UnknownHostException {

        //List followers = new ArrayList<>();
        //fetch the login details for the ID
        System.out.println(id);
        String sql1 = "SELECT followeeID,followeeName,followeeImgURL,followeeRole FROM follow WHERE followerID = '" + id + "'";
        List followers = jdbcTemplate.queryForList(sql1);
        System.out.println(followers);
        //return followers;

        int intID = Integer.parseInt(id);
        //RecommendationService recommendationService = new RecommendationService();
        List<JSONObject> recommendedUsers = recommendationService.getTopThreeFriends(intID);
        System.out.println(recommendedUsers);

        for(JSONObject reco : recommendedUsers)
            followers.add(reco);

        System.out.println(followers);
        return followers;
    }
}
