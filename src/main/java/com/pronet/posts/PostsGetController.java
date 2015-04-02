package com.pronet.posts;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.opsworks.model.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.validation.Valid;
import java.sql.Types;

/**
 * Created by parin on 4/2/15.
 */


public class PostsGetController {
    @RestController
    @Component("PostsGetController")
public class PostGetController {

        @Autowired
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        @Autowired
        DynamoDB dyDB;

        @RequestMapping(value = "/posts", method = RequestMethod.GET)
        @ResponseBody
        public void savePost(@Valid @RequestBody PostsModel model, BindingResult result) {



//            String desc = model.getDescription();
//            String providerId = model.getProviderId();
//            String title = model.getTitle();

            String sql = "SELECT * FROM Feeds ";
            String posts = jdbcTemplate.queryForObject(sql, String.class);
            System.out.println("Posts: "+posts);



            //System.out.println(desc + " " + providerId + " " + title);
            //   String sql = "Insert into Feeds (providerId, title, description) values (?,?,?)";


        }

    }
}
