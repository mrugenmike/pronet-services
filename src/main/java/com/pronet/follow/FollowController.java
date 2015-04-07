package com.pronet.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RestController
@Component("FollowController")
@RequestMapping("/api/v1")
public class FollowController {

    FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@PathVariable("id") String id, @Valid @RequestBody Follow follow) throws EmptyResultDataAccessException {


        followService.followAt(id,follow);
    }

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void Unfollow(@PathVariable("id") String id, @Valid @RequestBody Follow follow) throws EmptyResultDataAccessException {
      followService.UnfollowAt(id,follow);
    }

    @RequestMapping(value = "/following/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List following(@PathVariable("id") String id) {

        return followService.followingAt(id);

    }
}
