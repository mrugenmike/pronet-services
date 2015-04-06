package com.pronet.feeds;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.pronet.BadRequestException;
import com.pronet.DataBaseException;
import com.pronet.company.CompanyDetails;
import com.pronet.userdetails.UserDetails;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("FeedsService")
public class FeedsService {

    @Autowired
    DynamoDB dyDB;

    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public FeedsService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //POST new user feed
    public FeedsModel newUserFeedAt(String id, FeedsModel feed) throws Exception {
        feed.setUserID(id);
        feed.setFeed_role("U");

        try{

            jdbcTemplate.execute(
                    "INSERT INTO feeds(user_id,feed_title,feed_description,feed_role) values('"
                            + feed.getUserID() + "','"
                            + feed.getFeed_title()+ "','"
                            + feed.getFeed_description() + "','"
                            + feed.getFeed_role()+"')");


        }
        catch (Exception e)
        {
            throw new DataBaseException(e.getMessage());
        }

        return feed;

    }

    //POST new Company Feed
    public FeedsModel newCompanyFeedAt(String id, FeedsModel feed) throws Exception {
        feed.setUserID(id);
        feed.setFeed_role("C");

        try{

            jdbcTemplate.execute(
                    "INSERT INTO feeds(user_id,feed_title,feed_description,feed_role) values('"
                            + feed.getUserID() + "','"
                            + feed.getFeed_title()+ "','"
                            + feed.getFeed_description() + "','"
                            + feed.getFeed_role()+"')");


        }
        catch (Exception e)
        {
            throw new DataBaseException(e.getMessage());
        }

        return feed;

    }

    public JSONObject getUserFeedAt(String id) throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        try {

            String getUserFeeds = "SELECT feed_title,feed_description FROM feeds WHERE user_id ='"
                    + id + "' and feed_role = 'U'";

            List tmp = jdbcTemplate.queryForList(getUserFeeds);

            UserDetails getUser = mapper.load(UserDetails.class, id);
            jsonObject.put("user_name",getUser.getUser_name());

            jsonObject.put("feeds",tmp);
            return jsonObject;
        }
        catch(EmptyResultDataAccessException e)
        {

            throw new BadRequestException(e.getMessage());
        }

    }

    public JSONObject getCompanyFeedAt(String id) throws Exception
    {

        JSONObject jsonObject = new JSONObject();
        try {

            String getUserFeeds = "SELECT feed_title,feed_description FROM feeds WHERE user_id ='"
                    + id + "' and feed_role = 'C'" ;

            List tmp = jdbcTemplate.queryForList(getUserFeeds);

            CompanyDetails getCompany = mapper.load(CompanyDetails.class, id);
            jsonObject.put("user_name",getCompany.getUser_name());
            jsonObject.put("feeds",tmp);
            return jsonObject;
        }
        catch(EmptyResultDataAccessException e)
        {

            throw new BadRequestException(e.getMessage());
        }

    }
}
