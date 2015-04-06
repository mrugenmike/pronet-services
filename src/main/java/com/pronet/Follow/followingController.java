package com.pronet.Follow;

/**
 * Created by varuna on 4/2/15.
 */

import com.pronet.BadRequestException;
import com.pronet.signup.signUpModel;
import com.pronet.signup.signUpRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by varuna on 4/2/15.
 */

@Controller
@RestController
@Component("FollowingController")
public class followingController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @RequestMapping(value = "/following/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List following(@PathVariable("id") String id) {

        //List followers = new ArrayList<>();
        //fetch the login details for the ID
        System.out.println(id);
            String sql1 = "SELECT followeeID,followeeName,followeeImgURL,followeeRole FROM follow WHERE followerID = '" + id + "'";
            List followers = jdbcTemplate.queryForList(sql1);
            System.out.println(followers);
            return followers;

    }
}