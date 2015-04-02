package com.pronet.posts;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.opsworks.model.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.validation.Valid;
import java.sql.Types;

/**
 * Created by parin on 3/31/15.
 */
@RestController
@Component("PostsController")
public class PostController {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DynamoDB dyDB;

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public void savePost(@Valid @RequestBody PostsModel model,BindingResult result) {


        String desc = model.getDescription();
        String providerId = model.getProviderId();
        String title = model.getTitle();
        String role= model.getRole();

        System.out.println(desc + " "+providerId + " "+title+" "+role);
     //   String sql = "Insert into Feeds (providerId, title, description) values (?,?,?)";

        String insertSql = "INSERT INTO Feeds ( providerId,title,description) VALUES (?, ?, ?)";

        // define query arguments
        Object[] params = new Object[]{providerId,title,desc,role };

        // define SQL types of the arguments
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        int row = jdbcTemplate.update(insertSql, params, types);
        System.out.println(row + " row inserted.");
    }


}
