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
    public void newUserFeedAt(String id, FeedsModel feed) throws Exception {
        try{
            System.out.println(feed.getFeed_description() + " " + feed.getFeed_username() + " " +  feed.getFeed_userimage() + " " +
                    feed.getFeed_role() + " " + feed.getFeed_title());
            jdbcTemplate.execute(
                    "INSERT INTO feeds(user_id,feed_title,feed_description,feed_role,user_name,user_img) values('"
                            + id + "','"
                            + feed.getFeed_title()+ "','"
                            + feed.getFeed_description() + "','"
                            + feed.getFeed_role()+ "','"
                            + feed.getFeed_username() + "','"
                            + feed.getFeed_userimage() + "')");

        }
        catch (Exception e)
        {
            System.out.println("Unable to insert");
            throw new BadRequestException("unable to insert into feeds table.");
            //throw new DataBaseException(e.getMessage());
        }
    }

    public List getUserFeedAt(String id) throws Exception
    {
        try {

            String getUserFeeds = " SELECT feeds_id,user_id,feed_title,feed_description,feed_role,user_name,user_img from feeds where user_id in (select followeeID from follow where followerID = '"
                    + id + "') or user_id = '" + id + "' ORDER BY feed_id DESC";
            List userFeeds = jdbcTemplate.queryForList(getUserFeeds);
            return userFeeds;
        }

        catch(EmptyResultDataAccessException e)
        {

            throw new BadRequestException(e.getMessage());
        }

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
