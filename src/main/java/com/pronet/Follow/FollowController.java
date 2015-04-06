package com.pronet.Follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller
@RestController
@Component("FollowController")
public class FollowController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@PathVariable("id") String id, @Valid @RequestBody Follow follow, BindingResult result) throws EmptyResultDataAccessException {
        System.out.println("In follow");
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

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void Unfollow(@PathVariable("id") String id, @Valid @RequestBody Follow follow, BindingResult result) throws EmptyResultDataAccessException {

        System.out.println("In Unfollow");
        String sql = "SELECT count(1) FROM follow WHERE followerID ='" + id + "' and followeeID ='" +  follow.getFollowerId() + "'";
        Integer foll = jdbcTemplate.queryForObject(sql, Integer.class);

        if(foll == 0)
            return;
        jdbcTemplate.execute(" DELETE FROM follow where followerID = '" + id + "' and followeeID = '" + follow.getFollowerId() + "'");
        System.out.println("Deleted successfully!!");
    }
}
