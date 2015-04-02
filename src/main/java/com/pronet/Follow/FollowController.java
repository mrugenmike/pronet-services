package com.pronet.Follow;

import com.pronet.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pronet.Follow.Follow;
import javax.validation.Valid;

/**
 * Created by varuna on 4/2/15.
 */

@Controller
@RestController
@Component("FollowController")
public class FollowController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@PathVariable("id") String id, @Valid @RequestBody Follow follow, BindingResult result) throws EmptyResultDataAccessException {


        System.out.println(follow.getFollowerId() + follow.getFollowerName() + follow.getFollowerURL()+follow.getFollowerRole() + id);
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
        jdbcTemplate.execute(
                "DELETE FROM follow where followerID = '" + id + "' and followeeID = '" + follow.getFollowerId() + "'");
    }
}
